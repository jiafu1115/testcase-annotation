package com.googlecode.testcase.annotation.listeners;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.googlecode.testcase.annotation.TestCase;
import com.googlecode.testcase.annotation.handle.toexcel.TestCaseFileGenerator;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;

 
public class TestNGCaseLogListener implements ITestListener {

	private static final Logger LOGGER = Logger
			.getLogger(TestNGCaseLogListener.class);
	
	private TestCaseFileGenerator testCaseGenerator;
 
	@Override
    public void onStart(ITestContext arg0) {
 		Properties properties = System.getProperties();
  		Set<Entry<Object, Object>> entrySet = properties.entrySet();
  		
  		HashMap<String, String> options = new HashMap<String,String>();
  		for(Entry<Object, Object> entry: entrySet){
  			options.put(entry.getKey().toString(),entry.getValue().toString());
  		}
 		
 		testCaseGenerator = new TestCaseFileGenerator(
				options) ;
 
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
		Annotation[] annotations = method.getAnnotations();
 	 
		for (Annotation annotation : annotations) {		
  
			if (annotation instanceof TestCase) {
				
				TestCaseWrapper testCaseWrapper=new TestCaseWrapper((TestCase)annotation, method.toString());
				testCaseGenerator.addTestCaseWrapper(testCaseWrapper);
				
 				TestCase testCase = (TestCase) annotation;
				String title = testCase.title();
				String[] preConditions = testCase.preConditions();
				String[] steps = testCase.steps();
				String[] results = testCase.results();

				LOGGER.info("==========================================="+"TestCase:"+method+"===========================================");
				LOGGER.info("Case Title: ");
				LOGGER.info("            " + title);
				LOGGER.info("Case preConditions: ");
				for (int i = 0; i < preConditions.length; i++) {
					LOGGER.info("            " + (i+1) + " " + preConditions[i]);
				}

				LOGGER.info("Case steps: ");
				for (int i = 0; i < steps.length; i++) {
					LOGGER.info("            " + (i+1) + " " + steps[i]);
				}

				LOGGER.info("Case results: ");
 				for (int i = 0; i < results.length; i++) {
					LOGGER.info("            " + (i+1) + " " + results[i]);
				}

				LOGGER.info("======================================================================================================================");
 			}

		}

	}

	@Override
	public void onTestSuccess(ITestResult arg0) {
 	}

}
