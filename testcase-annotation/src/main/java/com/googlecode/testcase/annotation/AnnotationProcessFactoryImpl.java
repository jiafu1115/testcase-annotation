package com.googlecode.testcase.annotation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;


/**
 * @author jiafu
 *
 */
public class AnnotationProcessFactoryImpl implements AnnotationProcessorFactory {

	//to fix the multi-classloader issue:A "org.apache.log4j.ConsoleAppender" object is not assignable to a "org.apache.log4j.Appender" variable.
	static{
		System.setProperty("log4j.ignoreTCL", "true");
	}

	public Collection<String> supportedOptions() {
		return Collections.emptyList();
	}

	public Collection<String> supportedAnnotationTypes() {
		return Collections.unmodifiableCollection(Arrays.asList("com.googlecode.testcase.annotation.TestCase"));
	}

	public AnnotationProcessor getProcessorFor(
			Set<AnnotationTypeDeclaration> annotationTypeDeclarationSet,
			AnnotationProcessorEnvironment annotationProcessorEnvironment) {
		return new TestCaseAnnotationProcessorImpl(
				annotationProcessorEnvironment);
	}

}