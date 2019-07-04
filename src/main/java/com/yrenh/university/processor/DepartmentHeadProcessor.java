package com.yrenh.university.processor;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrenh.university.entity.Department;
import com.yrenh.university.repository.DepartmentRepository;
import com.yrenh.university.repository.LectorRepository;

@Service
public class DepartmentHeadProcessor implements CommandProcessor {

	private static final Pattern pattern = Pattern.compile("^Who is head of department .+");
	private static final String DEPARTMENT_DOES_NOT_EXIST_MESSAGE = "Department '%s' does not exist.";
	private DepartmentRepository departmentRepository;
	private LectorRepository lectorRepository;

	@Autowired
	public DepartmentHeadProcessor(DepartmentRepository departmentRepository, LectorRepository lectorRepository) {
		this.departmentRepository = departmentRepository;
		this.lectorRepository = lectorRepository;
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
				answer = String.format("Head of %s department is %s.", departmentName, department.get().getHead().getFirstName() + " " + department.get().getHead().getLastName());
			} else {
				answer = String.format("Department %s does not have head.", departmentName);
			}
		} else {
			answer = String.format(DEPARTMENT_DOES_NOT_EXIST_MESSAGE, departmentName);
		}
		return answer;
	}
}
