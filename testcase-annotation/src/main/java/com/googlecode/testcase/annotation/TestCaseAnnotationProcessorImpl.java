package com.googlecode.testcase.annotation;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.googlecode.testcase.annotation.handle.toexcel.strategy.ToXslExcelStrategy;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

class TestCaseAnnotationProcessorImpl implements AnnotationProcessor {

	private static final  Logger LOGGER=Logger.getLogger(TestCaseAnnotationProcessorImpl.class);
 	private static final String XLS_EXTENSION = ".xls";

	private final AnnotationProcessorEnvironment annotationProcessorEnvironment;

	TestCaseAnnotationProcessorImpl(AnnotationProcessorEnvironment annotationProcessorEnvironment) {
		this.annotationProcessorEnvironment = annotationProcessorEnvironment;
	}

	private static String generateExcelPath(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String date= simpleDateFormat.format(new Date());

 		return "TestCaes_"+date+XLS_EXTENSION;
	}

	public void process() {
		Map<String, String> options = this.annotationProcessorEnvironment.getOptions();
		LOGGER.info(String.format("[process][options map] %s",options));

		String file = getFileName(options);
 		String folder = options.get("-s");
		ToXslExcelStrategy toExcelHandle = new ToXslExcelStrategy(folder,file);

		for (TypeDeclaration typeDeclaration : annotationProcessorEnvironment.getSpecifiedTypeDeclarations()) {
 			LOGGER.debug(String.format("[process][type]found type: %s",typeDeclaration));
 			Collection<? extends MethodDeclaration> methods = typeDeclaration.getMethods();
			for (MethodDeclaration methodDeclaration : methods) {
   				String methodName=typeDeclaration+"."+methodDeclaration;
 				LOGGER.debug(String.format("[process][method]found method: %s",methodName));

 				TestCase testCase = methodDeclaration.getAnnotation(TestCase.class);
 				String simpleNameForTestCase = TestCase.class.getSimpleName();

 				if (testCase == null){
					LOGGER.debug(String.format("[process][method] %s has no the annotation: %s", methodName,simpleNameForTestCase));
					continue;
 				}

 				LOGGER.debug(String.format("[process][method] %s has the annotation: %s", methodName, simpleNameForTestCase));
				TestCaseWrapper testCaseWrapper = new TestCaseWrapper(testCase, methodName);
				LOGGER.info(testCaseWrapper);
				toExcelHandle.add(testCaseWrapper);
			}
		}

		toExcelHandle.generateExcelFile();
	}

	private String getFileName(Map<String, String> options) {
		String fileName=generateExcelPath();
		Set<String> keySet = options.keySet();
		for(String key:keySet){
			if(key.startsWith("-AfileName="))
				fileName=key.substring("-AfileName=".length());
 		};
		return fileName;
	}

}