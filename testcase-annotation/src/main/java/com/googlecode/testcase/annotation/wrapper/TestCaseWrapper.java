package com.googlecode.testcase.annotation.wrapper;

import java.util.Arrays;
import java.util.List;

import com.googlecode.testcase.annotation.TestCase;

public class TestCaseWrapper {

	public static enum TestCaseWrapperElement {

		ID, TITLE, PRECONDITIONS, STEPS, RESULTS, METHOD;

		public String toString() {
			return super.toString().toLowerCase();
		}

		public static List<TestCaseWrapperElement> toListAsSequence() {
			return Arrays.asList(TestCaseWrapperElement.values());
		}
	}

	private TestCase testCase;
	/**
	 * test case method in test project
	 */
	private String methodName;
	private String moduleName;

	public TestCaseWrapper(TestCase testCase, String methodName) {
		super();
		this.testCase = testCase;
		this.methodName = methodName;
		this.moduleName=testCase.module();
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

 	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	@Override
	public String toString() {
		return "TestCaseWrapper [testCase=" + testCase + ", methodName="
				+ methodName + ", moduleName=" + moduleName + "]";
	}

}
