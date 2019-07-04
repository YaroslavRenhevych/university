package com.yrenh.university;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.yrenh.university.config.AppConfig;
import com.yrenh.university.processor.AverageSalaryProcessor;
import com.yrenh.university.processor.CommandProcessor;
import com.yrenh.university.processor.DepartmentEmployeeCount;
import com.yrenh.university.processor.DepartmentHeadProcessor;
import com.yrenh.university.processor.DepartmentStatisticProcessor;
import com.yrenh.university.processor.LectorGlobalSearch;

public class UniversityApp {
	private Scanner commandLine;
	private ApplicationContext context;
	private List<CommandProcessor> commandProcessors;

	public void init() {
		commandLine = new Scanner(System.in);
		context = new AnnotationConfigApplicationContext(AppConfig.class);
		commandProcessors = Arrays.asList(context.getBean(DepartmentHeadProcessor.class),
				context.getBean(DepartmentStatisticProcessor.class),
				context.getBean(AverageSalaryProcessor.class),
				context.getBean(DepartmentEmployeeCount.class),
				context.getBean(LectorGlobalSearch.class));
	}

	public void start() {
		System.out.println("Welcome!");
		printAllowedCommand();
		while(true) {
			String command = commandLine.nextLine();
			String answer = processCommand(command);
			System.out.println("Answer: " + answer);
			System.out.println("Try again:");
		}
	}

	private void printAllowedCommand() {
		System.out.println("Allowable command:");
		System.out.println("1. \"Who is head of department {department_name}\"");
		System.out.println("2. \"Show {department_name} statistic\"");
		System.out.println("3. \"Show the average salary for department {department_name}\"");
		System.out.println("4. \"Show count of employee for {department_name}\"");
		System.out.println("5. \"Global search by {template}\"");
		System.out.println("6. \"exit\"");
		System.out.println("Please, enter your command:");
	}

	private String processCommand(String command) {
		String answer = "Wrong command!";

		if("exit".equals(command)) {
			System.out.println("Goodbye!");
			System.exit(0);
		}

		for(CommandProcessor commandProcessor: commandProcessors) {
			if(commandProcessor.acceptableCommand(command)) {
				answer = commandProcessor.process(command);
			}
		}
		return answer;
	}
}
