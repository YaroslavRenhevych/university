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

	private static final String EXIT_COMMAND = "exit";
	private static final String WELCOME = "Welcome!";
	private static final String ANSWER = "Answer: ";
	private static final String TRY_AGAIN = "Try again:";
	private static final String ALLOWED_COMMANDS_MSG = "Allowable command:\n" +
		"1. \"Who is head of department {department_name}\"\n" +
		"2. \"Show {department_name} statistic\"\n" +
		"3. \"Show the average salary for department {department_name}\"\n" +
		"4. \"Show count of employee for {department_name}\"\n" +
		"5. \"Global search by {template}\"\n" +
		"6. \"" + EXIT_COMMAND + "\"\n" +
		"Please, enter your command:";
	private static final String WRONG_COMMAND = "Wrong command!";
	private static final String GOODBYE = "Goodbye!";
	private static final int SUCCESSFUL_EXIT_STATUS = 0;

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
		System.out.println(WELCOME);
		System.out.println(ALLOWED_COMMANDS_MSG);
		while(true) {
			String command = commandLine.nextLine();
			String answer = processCommand(command);
			System.out.println(ANSWER + answer);
			System.out.println(TRY_AGAIN);
		}
	}

	private String processCommand(String command) {
		String answer = WRONG_COMMAND;

		if(EXIT_COMMAND.equals(command)) {
			System.out.println(GOODBYE);
			System.exit(SUCCESSFUL_EXIT_STATUS);
		}

		for(CommandProcessor commandProcessor: commandProcessors) {
			if(commandProcessor.acceptableCommand(command)) {
				answer = commandProcessor.process(command);
				break;
			}
		}
		return answer;
	}
}
