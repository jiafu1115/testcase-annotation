package com.googlecode.testcase.annotation;

import java.util.Collection;

import org.apache.log4j.Logger;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

class TestCaseAnnotationProcessorImpl implements AnnotationProcessor {

	private final static Logger LOGGER=Logger.getLogger(TestCaseAnnotationProcessorImpl.class);
	private final AnnotationProcessorEnvironment annotationProcessorEnvironment;

	TestCaseAnnotationProcessorImpl(AnnotationProcessorEnvironment annotationProcessorEnvironment) {
		this.annotationProcessorEnvironment = annotationProcessorEnvironment;
	}

	public void process() {
		ToExcelHandle toExcelHandle = new ToExcelHandle();

		for (TypeDeclaration typeDeclaration : annotationProcessorEnvironment.getSpecifiedTypeDeclarations()) {
			System.out.println("find:" + typeDeclaration);

			Collection<? extends MethodDeclaration> methods = typeDeclaration.getMethods();
			for (MethodDeclaration methodDeclaration : methods) {

				System.out.println("___" + methodDeclaration);

				TestCase testCase = methodDeclaration.getAnnotation(TestCase.class);
				if (testCase == null)
					continue;

				TestCaseWrapper testCaseWrapper = new TestCaseWrapper(testCase, methodDeclaration.getSimpleName());
				LOGGER.info(testCaseWrapper);
				toExcelHandle.add(testCaseWrapper);
			}
		}

		toExcelHandle.generate();
	}

}