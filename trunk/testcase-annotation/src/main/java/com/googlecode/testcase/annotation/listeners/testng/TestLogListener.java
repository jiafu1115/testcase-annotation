package com.googlecode.testcase.annotation.listeners.testng;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import com.googlecode.testcase.annotation.TestCase;

/**
 * <pre>
 *             <plugin>
 *                 <groupId>org.apache.maven.plugins</groupId>
 *                 <artifactId>maven-surefire-plugin</artifactId>
 *                 <version>2.15</version>
 *                 <configuration>
 *                     <suiteXmlFiles>
 *                          <suiteXmlFile>${testNG.file}</suiteXmlFile>
 *                     </suiteXmlFiles>
 *                      <properties>
 *                         <property>
 *                             <name>usedefaultlisteners</name>
 *                             <value>true</value>
 *                             <!--  disabling default listeners is optional -->
 *                         </property>
 *                         <property>
 *                             <name>listener</name>
 *                             <value>com.googlecode.testcase.annotation.listeners.testng.TestLogListener</value>
 *                         </property>
 *                     </properties>
 * 
 *                 </configuration>
 *             </plugin>
 * </pre>
 * 
 * @author jiafu
 *
 */
public class TestCaseLogListener implements ITestListener {

	private static final Logger LOGGER = Logger
			.getLogger(TestCaseLogListener.class);

	@Override
    public void onStart(ITestContext arg0) {
 
    }

	@Override
	public void onFinish(ITestContext arg0) {
 
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
