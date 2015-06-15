package com.googlecode.testcase.annotation.listeners;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.runner.notification.RunListener;

import com.googlecode.testcase.annotation.TestCase;
import com.googlecode.testcase.annotation.handle.toexcel.TestCaseFileGenerator;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;

public class TestCaseListener extends RunListener{
	
	private static final Logger LOGGER = Logger
			.getLogger(TestCaseListener.class);
	
	protected TestCaseFileGenerator testCaseGenerator;
	 
    public TestCaseListener( ) {
 		Properties properties = System.getProperties();
  		Set<Entry<Object, Object>> entrySet = properties.entrySet();
  		
  		HashMap<String, String> options = new HashMap<String,String>();
  		for(Entry<Object, Object> entry: entrySet){
  			options.put(entry.getKey().toString(),entry.getValue().toString());
  		}
 		
 		testCaseGenerator = new TestCaseFileGenerator(
				options) ;
 
    }
    
 	public void onFinish() {
 		testCaseGenerator.process();
 	}
 	
 	
 	public void printAndAddTestCase(String methodName,
			TestCase annotation) {
		TestCaseWrapper testCaseWrapper=new TestCaseWrapper(annotation, methodName);
		testCaseGenerator.addTestCaseWrapper(testCaseWrapper);
		
		TestCase testCase = (TestCase) annotation;
		String title = testCase.title();
		String[] preConditions = testCase.preConditions();
		String[] steps = testCase.steps();
		String[] results = testCase.results();

		LOGGER.info("==========================================="+"TestCase:"+methodName+"===========================================");
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
