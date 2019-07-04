package com.yrenh.university.processor;

import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yrenh.university.entity.Degree;
import com.yrenh.university.entity.Department;
import com.yrenh.university.repository.DepartmentRepository;
import com.yrenh.university.repository.LectorRepository;
@Service
public class DepartmentStatisticProcessor implements CommandProcessor {

	private static final Pattern pattern = Pattern.compile("^Show .+ statistic$");
	private static final String DEPARTMENT_DOES_NOT_EXIST_MESSAGE = "Department '%s' does not exist.";
	private static final String SUBSTRING_AFTER_DEPARTMENT_NAME_IN_CMD = " statistic";
	private static final int INDEX_OF_DEPARTMENT_NANE_IN_CMD = 5;
	private static final String SUCCESS_ANSWER = "assistans - %d.\n" +
												"associate professors - %d\n" +
												"professors - %d";

	private DepartmentRepository departmentRepository;
	private LectorRepository lectorRepository;

	@Autowired
	public DepartmentStatisticProcessor(DepartmentRepository departmentRepository, LectorRepository lectorRepository) {
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
		String departmentName = command.substring(INDEX_OF_DEPARTMENT_NANE_IN_CMD, command.lastIndexOf(SUBSTRING_AFTER_DEPARTMENT_NAME_IN_CMD));
		Optional<Department> department = departmentRepository.findByName(departmentName);
		if(department.isPresent()) {
			int assistentsCount = lectorRepository.findByDepartmentsAndDegree(department.get(), Degree.ASSISTANT).size();
			int associateProfessorsCount = lectorRepository.findByDepartmentsAndDegree(department.get(), Degree.ASSOCIATE_PROFESSOR).size();
			int professorsCount = lectorRepository.findByDepartmentsAndDegree(department.get(), Degree.PROFESSOR).size();
			answer = String.format(SUCCESS_ANSWER, assistentsCount, associateProfessorsCount, professorsCount);
		} else {
			answer = String.format(DEPARTMENT_DOES_NOT_EXIST_MESSAGE, departmentName);
		}
		return answer;
	}
}
