package com.googlecode.testcase.annotation;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.TypeDeclaration;

class TestCaseAnnotationProcessorImpl implements AnnotationProcessor {
	private final AnnotationProcessorEnvironment annotationProcessorEnvironment;

	TestCaseAnnotationProcessorImpl(AnnotationProcessorEnvironment annotationProcessorEnvironment) {
		this.annotationProcessorEnvironment = annotationProcessorEnvironment;
	}

	public void process() {
 		for (TypeDeclaration typeDeclaration : annotationProcessorEnvironment.getSpecifiedTypeDeclarations()){

	}
	}


}