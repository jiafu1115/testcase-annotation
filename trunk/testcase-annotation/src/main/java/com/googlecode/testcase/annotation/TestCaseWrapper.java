package com.googlecode.testcase.annotation;

public class TestCaseWrapper {

	private TestCase testCase;
	/**
	 * test case method in test project
	 */
	private String methodName;

	public TestCaseWrapper(TestCase testCase, String methodName) {
		super();
		this.testCase = testCase;
		this.methodName = methodName;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TestCaseWrapper [testCaseWrapper=");
		builder.append(testCase);
		builder.append(", methodName=");
		builder.append(methodName);
		builder.append("]");
		return builder.toString();
	}

}
