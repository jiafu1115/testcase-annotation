package com.googlecode.testcase.annotation;

import java.util.Collection;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.AnnotationMirror;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

class TestCaseAnnotationProcessorImpl implements AnnotationProcessor {
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

				toExcelHandle.add(new TestCaseWrapper(testCase, methodDeclaration.getSimpleName()));
				System.out.println(testCase.results()[0]);
			}
		}

		toExcelHandle.generate();
	}

}