package com.googlecode.testcase.annotation.listeners;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.googlecode.testcase.annotation.TestCase;

 
public class TestNGTestCaseListener extends TestCaseListener implements ITestListener {

 	@Override
    public void onStart(ITestContext arg0) {
  
    }

	@Override
	public void onFinish(ITestContext arg0) {
 		testCaseGenerator.process();
 	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
 
	}
  
	@Override
	public void onTestFailure(ITestResult arg0) {
 
	}

	@Override
	public void onTestSkipped(ITestResult arg0) {

	}

	@Override
	public void onTestStart(ITestResult iTestResult) {
   		@SuppressWarnings("deprecation")
		Method method = iTestResult.getMethod().getMethod();
  		String methodName = method.toString();
		TestCase annotation=method.getAnnotation(TestCase.class);;
  		
		if(annotation!=null)
			printAndAddTestCase( methodName, annotation);
 	}

	

	@Override
	public void onTestSuccess(ITestResult arg0) {
 	}

}
