package com.yrenh.university.processor;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrenh.university.entity.Department;
import com.yrenh.university.repository.DepartmentRepository;
import com.yrenh.university.repository.LectorRepository;

@Service
public class DepartmentEmployeeCount implements CommandProcessor {

	private static final Pattern pattern = Pattern.compile("^Show count of employee for .+");
	private static final String DEPARTMENT_DOES_NOT_EXIST_MESSAGE = "Department '%s' does not exist.";
	private DepartmentRepository departmentRepository;
	private LectorRepository lectorRepository;

	@Autowired
	public DepartmentEmployeeCount(DepartmentRepository departmentRepository, LectorRepository lectorRepository) {
		this.departmentRepository = departmentRepository;
		this.lectorRepository = lectorRepository;
	}

	@Override
	public boolean acceptableCommand(String command) {
		return pattern.matcher(command).find();
	}

	@Override
	public String process(String command) {
		String answer;
		String departmentName = command.substring(27);
		Optional<Department> department = departmentRepository.findByName(departmentName);
		if(department.isPresent()) {
			answer = String.format("%d", department.get().getLectors().size());
		} else {
			answer = String.format(DEPARTMENT_DOES_NOT_EXIST_MESSAGE, departmentName);
		}
		return answer;
	}
}
