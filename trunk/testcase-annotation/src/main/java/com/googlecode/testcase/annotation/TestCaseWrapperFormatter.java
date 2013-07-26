package com.googlecode.testcase.annotation;

public abstract class TestCaseWrapperFormatter {

	protected TestCaseWrapper testCaseWrapper;

	protected TestCaseWrapperFormatter(TestCaseWrapper testCase) {
		super();
		this.testCaseWrapper = testCase;
	}

	public abstract String format(CaseElementWrapper caseElementWrapper);

}