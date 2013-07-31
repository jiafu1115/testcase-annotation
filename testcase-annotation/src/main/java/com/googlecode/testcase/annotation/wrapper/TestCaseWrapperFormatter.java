package com.googlecode.testcase.annotation.wrapper;

import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper.TestCaseWrapperElement;

public abstract class TestCaseWrapperFormatter {

	protected TestCaseWrapper testCaseWrapper;

	protected TestCaseWrapperFormatter(TestCaseWrapper testCaseWrapper) {
		super();
		this.testCaseWrapper = testCaseWrapper;
	}

	public abstract String format(TestCaseWrapperElement testCaseWrapperElement);

}