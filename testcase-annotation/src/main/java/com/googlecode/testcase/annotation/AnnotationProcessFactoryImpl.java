package com.googlecode.testcase.annotation;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.apt.AnnotationProcessorFactory;
import com.sun.mirror.declaration.AnnotationTypeDeclaration;

public class AnnotationProcessFactoryImpl implements AnnotationProcessorFactory {

	private static final Collection<String> supportedAnnotations = unmodifiableCollection(Arrays
			.asList("*"));

	private static final Collection<String> supportedOptions = emptySet();

	public Collection<String> supportedAnnotationTypes() {
		return supportedAnnotations;
	}

	public Collection<String> supportedOptions() {
		return supportedOptions;
	}

	public AnnotationProcessor getProcessorFor(
			Set<AnnotationTypeDeclaration> annotationTypeDeclarationSet,
			AnnotationProcessorEnvironment annotationProcessorEnvironment) {
		return new TestCaseAnnotationProcessorImpl(annotationProcessorEnvironment);
	}
}