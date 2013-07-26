package com.googlecode.testcase.annotation;

import com.googlecode.testcase.annotation.TestCaseWrapper.TestCaseWrapperElement;

public abstract class TestCaseWrapperFormatter {

	protected TestCaseWrapper testCaseWrapper;

	protected TestCaseWrapperFormatter(TestCaseWrapper testCaseWrapper) {
		super();
		this.testCaseWrapper = testCaseWrapper;
	}

	public abstract String format(TestCaseWrapperElement testCaseWrapperElement);

}