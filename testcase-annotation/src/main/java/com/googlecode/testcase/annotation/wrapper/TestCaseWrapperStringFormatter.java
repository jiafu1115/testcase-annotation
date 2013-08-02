package com.googlecode.testcase.annotation.wrapper;

import com.googlecode.testcase.annotation.TestCase;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper.TestCaseWrapperElement;

public class TestCaseWrapperStringFormatter extends TestCaseWrapperFormatter {

	public static int getColumnWidth(TestCaseWrapperElement testCaseWrapperElement) {
  		switch (testCaseWrapperElement) {
		case ID:
			return 1500;
		case TITLE:
 		case PRECONDITIONS:
 		case STEPS:
 		case RESULTS:
		case METHOD:
			return 11000;
		default:
			throw new AssertionError("no key");
		}
	}


	public TestCaseWrapperStringFormatter(TestCaseWrapper testCaseWrapper) {
		super(testCaseWrapper);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.googlecode.testcase.annotation.TestCaseWrapperFormatter#format(com.googlecode
	 * .testcase.annotation.CaseElement)
	 */
	public String format(TestCaseWrapperElement testCaseWrapperElement) {
		TestCase testCase = testCaseWrapper.getTestCase();
  		switch (testCaseWrapperElement) {
		case ID:
			return String.valueOf(testCase.id());
		case TITLE:
			return testCase.title();
		case PRECONDITIONS:
			return formatStrArray(testCase.preConditions());
		case STEPS:
			return formatStrArray(testCase.steps());
		case RESULTS:
			return formatStrArray(testCase.results());
		case METHOD:
			return testCaseWrapper.getMethodName();
		default:
			throw new AssertionError("no key");
		}
	}

	private String formatStrArray(String[] originalStrArray) {
		if(originalStrArray.length==1&&originalStrArray[0].isEmpty())
			return "N/A";

		StringBuilder stringBuilder = new StringBuilder(100);
		for (int i = 0; i < originalStrArray.length; i++) {
			String formatStr = String.format("%d. %s\n", i + 1, originalStrArray[i]);
			stringBuilder.append(formatStr);
		}

		return stringBuilder.toString();
 	}

}
