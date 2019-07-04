package com.yrenh.university.processor;

public interface CommandProcessor {

	boolean acceptableCommand(String command);

	String process(String command);
}
