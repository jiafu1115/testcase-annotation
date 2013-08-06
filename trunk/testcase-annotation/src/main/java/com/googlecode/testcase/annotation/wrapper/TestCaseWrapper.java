package com.googlecode.testcase.annotation.wrapper;

import java.util.Arrays;
import java.util.List;

import com.googlecode.testcase.annotation.TestCase;

public class TestCaseWrapper implements Comparable<TestCaseWrapper>{

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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((methodName == null) ? 0 : methodName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCaseWrapper other = (TestCaseWrapper) obj;
		if (methodName == null) {
			if (other.methodName != null)
				return false;
		} else if (!methodName.equals(other.methodName))
			return false;
		return true;
	}

	public int compareTo(TestCaseWrapper o) {
		if(moduleName.equalsIgnoreCase(o.getModuleName())){
			if(testCase.id()==o.getTestCase().id()){
				return 0;
			}else{
				return testCase.id()-o.getTestCase().id();
			}
		}else
			return moduleName.compareToIgnoreCase(o.getModuleName());
 	}

}
