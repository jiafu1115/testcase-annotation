package com.googlecode.testcase.annotation;

public class TestCaseWrapperStringFormatter extends TestCaseWrapperFormatter {

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
	public String format(CaseElementWrapper caseElementWrapper) {
		TestCase testCase = testCaseWrapper.getTestCase();
  		switch (caseElementWrapper) {
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
		StringBuilder stringBuilder = new StringBuilder(100);
		for (int i = 0; i < originalStrArray.length; i++) {
			String formatStr = String.format("%d. %s\n", i + 1, originalStrArray[i]);
			stringBuilder.append(formatStr);
		}

		return stringBuilder.toString();
	}

}
