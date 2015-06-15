package com.googlecode.testcase.annotation.handle.toexcel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.googlecode.testcase.annotation.OptionsConstants;
import com.googlecode.testcase.annotation.handle.toexcel.strategy.ToExcelStrategy;
import com.googlecode.testcase.annotation.handle.toexcel.strategy.ToXlsExcelStrategy;
import com.googlecode.testcase.annotation.handle.toexcel.strategy.ToXlsxExcelStrategy;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;

public  class TestCaseFileGenerator  {

	private static final Logger LOGGER = Logger
			.getLogger(TestCaseFileGenerator.class);
	
	protected Map<String, String> options ;
	protected ToExcelStrategy toExcelHandle;
	protected boolean isForceCreateFile;
	protected List<TestCaseWrapper> testCaseWrapperList;
	
	public TestCaseFileGenerator(Map<String, String> options){
		this.options=options;
  		LOGGER.info(String.format("[processor][info][options map] %s", options));
  		this.toExcelHandle = getExcelStrategy();
	 	this.isForceCreateFile=isForceCreateFile();
	 	this.testCaseWrapperList=new ArrayList<TestCaseWrapper>();
	}
 	 
	
	public void addTestCaseWrapper(TestCaseWrapper testCaseWrapper){
		testCaseWrapperList.add(testCaseWrapper);
	}
	
 	public boolean process() {
 		for(TestCaseWrapper testCaseWrapper:testCaseWrapperList){
			if(LOGGER.isDebugEnabled())
				LOGGER.debug(String.format("[processor][discover][test case][info]annotation: %s", testCaseWrapper));
			toExcelHandle.add(testCaseWrapper);
		}
 		generateFile();
		
 		return true;
 	}
  
 	private void generateFile() {
		if(isForceCreateFile||toExcelHandle.isThereCase())
			toExcelHandle.generateFile();
	}

	private ToExcelStrategy getExcelStrategy() {
  		String file = getFileName();
		String folder =getFolderPath();
		 
  		ToExcelStrategy toExcelHandle = getExcelStrategy(file, folder);
		return toExcelHandle;
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

	private String getFileName() {
		String fileNameFromOptionConfig = options.get(OptionsConstants.FILE_NAME_OPTION);
		if (fileNameFromOptionConfig != null) {
			return fileNameFromOptionConfig;
		}

		return generateRandomFileName();
	}
	
	private String getFolderPath() {
		String fileNameFromOptionConfig = options.get(OptionsConstants.FOLDER_PATH_OPTION);
		if (fileNameFromOptionConfig != null) {
			return fileNameFromOptionConfig;
		}

		String defaultFolderPath = this.getClass().getClassLoader().getResource("").getPath();
		return defaultFolderPath;
	}

	private boolean isForceCreateFile() {
		String forceCreateFile = options.get(OptionsConstants.FORCE_CREATE_FILE_OPTION);
		if (forceCreateFile != null) {
			return Boolean.parseBoolean(forceCreateFile.trim().toLowerCase());
		}

		return false;
	}

	private  String generateRandomFileName() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HH-mm-ss");
		String date = simpleDateFormat.format(new Date());

		ExcelType excelType = ExcelConstants.DEFAULT_EXCEL_TYPE;
		String strExcelTypeFromOptionsConfig = options.get(OptionsConstants.EXCEL_TYPE_OPTION);
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


}
