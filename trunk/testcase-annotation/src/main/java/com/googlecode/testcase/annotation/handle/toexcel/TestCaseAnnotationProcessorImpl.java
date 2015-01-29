package com.googlecode.testcase.annotation.handle.toexcel;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.googlecode.testcase.annotation.OptionsConstants;
import com.googlecode.testcase.annotation.TestCase;
import com.googlecode.testcase.annotation.handle.toexcel.strategy.ToExcelStrategy;
import com.googlecode.testcase.annotation.handle.toexcel.strategy.ToXlsxExcelStrategy;
import com.googlecode.testcase.annotation.handle.toexcel.strategy.ToXlsExcelStrategy;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;

public class TestCaseAnnotationProcessorImpl implements AnnotationProcessor {

	private static final Logger LOGGER = Logger
			.getLogger(TestCaseAnnotationProcessorImpl.class);

	private final AnnotationProcessorEnvironment annotationProcessorEnvironment;

	public TestCaseAnnotationProcessorImpl(
			AnnotationProcessorEnvironment annotationProcessorEnvironment) {
		this.annotationProcessorEnvironment = annotationProcessorEnvironment;
	}

	public void process() {
		Map<String, String> options = this.annotationProcessorEnvironment
				.getOptions();
 		
		if(LOGGER.isDebugEnabled())
 			LOGGER.debug(String.format("[processor][info][options map] %s", options));

		String file = getFileName(options);
		String folder = options.get("-d");
		boolean isForceCreateFile=isForceCreateFile(options);

		ToExcelStrategy toExcelHandle = getExcelStrategy(file, folder);

		for (TypeDeclaration typeDeclaration : annotationProcessorEnvironment
				.getSpecifiedTypeDeclarations()) {
			if(LOGGER.isDebugEnabled())
				LOGGER.debug(String.format("[processor][discover][type][found]type: %s",
					typeDeclaration));

			Collection<? extends MethodDeclaration> methods = typeDeclaration
					.getMethods();
			for (MethodDeclaration methodDeclaration : methods) {
				String methodName = typeDeclaration + "." + methodDeclaration;
				if(LOGGER.isDebugEnabled())
				LOGGER.debug(String.format("[processor][discover][method][found]method: %s",
						methodName));

				TestCase testCase = methodDeclaration
						.getAnnotation(TestCase.class);
				String simpleNameForTestCase = TestCase.class.getSimpleName();

				if (testCase == null) {
					if (LOGGER.isDebugEnabled())
					LOGGER.debug(String.format(
							"[processor][discover][method][judge] %s has no the annotation: %s",
							methodName, simpleNameForTestCase));
					continue;
				}

				if(LOGGER.isDebugEnabled())
				LOGGER.debug(String.format(
						"[processor][discover][method][judge] %s has the annotation: %s",
						methodName, simpleNameForTestCase));
				TestCaseWrapper testCaseWrapper = new TestCaseWrapper(testCase,
						methodName);

				if(LOGGER.isDebugEnabled())
					LOGGER.debug(String.format("[processor][discover][test case][info]annotation: %s", testCaseWrapper));
				toExcelHandle.add(testCaseWrapper);
			}
		}

		if(isForceCreateFile||toExcelHandle.isThereCase())
			toExcelHandle.generateFile();
	}

	private ToExcelStrategy getExcelStrategy(String file, String folder) {
		ToExcelStrategy toExcelStrategy;
		if (file.endsWith(ExcelType.XLS.getExtension()))
			toExcelStrategy = new ToXlsExcelStrategy(folder, file);
 		else
			toExcelStrategy = new ToXlsxExcelStrategy(folder, file);

		LOGGER.info("[processor][stratgy][choose]"+toExcelStrategy.getClass().getSimpleName());
		LOGGER.info("[processor][stratgy][info]"+toExcelStrategy);

		return toExcelStrategy;
	}

	private String getFileName(Map<String, String> options) {
		String fileNameFromOptionConfig = getAvalueFromOptions(options,
				OptionsConstants.FILE_NAME_OPTION);
		if (fileNameFromOptionConfig != null) {
			return fileNameFromOptionConfig;
		}

		return generateRandomFileName(options);
	}

	private boolean isForceCreateFile(Map<String, String> options) {
		String forceCreateFile = getAvalueFromOptions(options,
				OptionsConstants.FORCE_CREATE_FILE_OPTION);
		if (forceCreateFile != null) {
			return Boolean.parseBoolean(forceCreateFile.trim().toLowerCase());
		}

		return false;
	}

	private static String generateRandomFileName(Map<String, String> options) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HH-mm-ss");
		String date = simpleDateFormat.format(new Date());

		ExcelType excelType = ExcelConstants.DEFAULT_EXCEL_TYPE;
		String strExcelTypeFromOptionsConfig = getAvalueFromOptions(options,
				OptionsConstants.EXCEL_TYPE_OPTION);
		if (strExcelTypeFromOptionsConfig != null) {
			ExcelType excelTypeFromOptionsConfig = ExcelType
					.fromString(strExcelTypeFromOptionsConfig);
			if (excelTypeFromOptionsConfig != null) {
				excelType = excelTypeFromOptionsConfig;
			} else {
				throw new UnsupportedOperationException(
						"not supported excelType: "
								+ strExcelTypeFromOptionsConfig);
			}
		}

		return String.format(ExcelConstants.DEFAULT_EXCEL_NAME_FORMAT, date, excelType);
	}

	private static String getAvalueFromOptions(Map<String, String> options,
			String keyword) {
		Set<String> keySet = options.keySet();
		for (String key : keySet) {
			String keyBeforeValue = "-A" + keyword + "=";
			if (key.startsWith(keyBeforeValue))
				return key.substring(keyBeforeValue.length());
		}
		;
		return null;
	}

}
