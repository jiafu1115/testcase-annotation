package com.googlecode.testcase.annotation.listeners;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.googlecode.testcase.annotation.TestCase;

public class JunitTestCaseListener extends TestCaseListener{
	
	public void testRunStarted(Description description) throws Exception {
	}

	public void testRunFinished(Result result) throws Exception {
 		testCaseGenerator.process();
 	}

	public void testStarted(Description description) throws Exception {
  		TestCase annotation=description.getAnnotation(TestCase.class);;
 		printAndAddTestCase( description.getMethodName(), annotation);
 	}

	public void testFinished(Description description) throws Exception {
		
	}

	public void testFailure(Failure failure) throws Exception {
	}

	public void testAssumptionFailure(Failure failure) {
	}

	public void testIgnored(Description description) throws Exception {
	}

}
