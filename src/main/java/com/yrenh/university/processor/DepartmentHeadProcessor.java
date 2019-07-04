package com.yrenh.university.processor;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrenh.university.entity.Department;
import com.yrenh.university.repository.DepartmentRepository;

@Service
public class DepartmentHeadProcessor implements CommandProcessor {

	private static final Pattern pattern = Pattern.compile("^Who is head of department .+");
	private static final String DEPARTMENT_DOES_NOT_EXIST_MESSAGE = "Department '%s' does not exist.";
	private static final String SECCESS_ANSWER = "Head of %s department is %s.";
	private static final String DEPARTMENT_DOESNT_HAVE_HEAD_ANSWER = "Department %s does not have head.";
	private static final String FIRSTNAME_LASTNAME_DELIMITER = " ";

	private DepartmentRepository departmentRepository;

	@Autowired
	public DepartmentHeadProcessor(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public boolean acceptableCommand(String command) {
		return pattern.matcher(command).find();
	}

	@Override
	public String process(String command) {
		String departmentName = command.substring(26);
		Optional<Department> department = departmentRepository.findByName(departmentName);
		String answer;
		if(department.isPresent()) {
			if(department.get().getHead() != null) {
				answer = String.format(SECCESS_ANSWER,
							departmentName,
							department.get().getHead().getFirstName()
									+ FIRSTNAME_LASTNAME_DELIMITER
									+ department.get().getHead().getLastName());
			} else {
				answer = String.format(DEPARTMENT_DOESNT_HAVE_HEAD_ANSWER, departmentName);
			}
		} else {
			answer = String.format(DEPARTMENT_DOES_NOT_EXIST_MESSAGE, departmentName);
		}
		return answer;
	}
}
