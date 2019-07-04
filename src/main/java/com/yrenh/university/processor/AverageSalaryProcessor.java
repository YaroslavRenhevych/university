package com.yrenh.university.processor;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrenh.university.entity.Department;
import com.yrenh.university.repository.DepartmentRepository;

@Service
public class AverageSalaryProcessor implements CommandProcessor {

	private static final String PATTERN_TEMPLATE = "^Show the average salary for department .+";
	private static final Pattern pattern = Pattern.compile(PATTERN_TEMPLATE);
	private static final String DEPARTMENT_DOES_NOT_EXIST_MESSAGE = "Department '%s' does not exist.";
	private static final String SECCESS_ANSWER = "The average salary of '%s' is %.2f";
	private static final String DEPARTMENT_DOESNT_HAVE_EMPLOYEES_ANSWER = "Department '%s' does not have employees";
	private static final int INDEX_OF_DEPARTMENT_NAME_IN_CMD = 39;

	private DepartmentRepository departmentRepository;

	@Autowired
	public AverageSalaryProcessor(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}
	@Override
	public boolean acceptableCommand(String command) {
		return pattern.matcher(command).find();
	}

	@Override
	public String process(String command) {
		String answer;
		String departmentName = command.substring(INDEX_OF_DEPARTMENT_NAME_IN_CMD);
		Optional<Department> department = departmentRepository.findByName(departmentName);
		if(department.isPresent()) {
			Float averageSalary = departmentRepository.getAverageSalaryByDepartmentName(departmentName);
			if(averageSalary != null) {
				answer = String.format(SECCESS_ANSWER, departmentName, averageSalary);
			} else {
				answer = String.format(DEPARTMENT_DOESNT_HAVE_EMPLOYEES_ANSWER, departmentName);
			}

		} else {
			answer = String.format(DEPARTMENT_DOES_NOT_EXIST_MESSAGE, departmentName);
		}
		departmentRepository.getAverageSalaryByDepartmentName(departmentName);
		return answer;
	}
}
