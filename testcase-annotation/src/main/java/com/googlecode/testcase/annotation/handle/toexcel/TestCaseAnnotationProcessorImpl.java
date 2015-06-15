package com.googlecode.testcase.annotation.handle.toexcel;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.googlecode.testcase.annotation.TestCase;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;

@SupportedAnnotationTypes("com.googlecode.testcase.annotation.TestCase")
public class TestCaseAnnotationProcessorImpl extends AbstractProcessor {

	static {
		System.setProperty("log4j.ignoreTCL", "true");
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {
		Map<String, String> options = this.processingEnv.getOptions();

		TestCaseFileGenerator abstractTestCaseGenerator = new TestCaseFileGenerator(
				options);

		Set<? extends Element> elementsForTestCase = roundEnv
				.getElementsAnnotatedWith(TestCase.class);
		for (Element element : elementsForTestCase) {
			TestCase annotation = element.getAnnotation(TestCase.class);
			TestCaseWrapper testCaseWrapper = new TestCaseWrapper(annotation,
					element.toString());

			abstractTestCaseGenerator.addTestCaseWrapper(testCaseWrapper);
		}

		abstractTestCaseGenerator.process();

		return true;
	}

}
