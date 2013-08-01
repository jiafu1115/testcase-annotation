package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper.TestCaseWrapperElement;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapperStringFormatter;


public abstract class AbstractToExcelStrategy implements ToExcelStrategy{

	private static final Logger LOGGER=Logger.getLogger(AbstractToExcelStrategy.class);

	private static final String SHEET_NAME = "Test Cases";
	private static int ROW_NUMBER = 0;

	protected String folder;
	protected String file;
	protected Workbook workbook;

	public AbstractToExcelStrategy(String folder,String file, Workbook workbook) {
		super();
		this.folder=folder;
		this.file=file;
		this.workbook=workbook;
		initialWorkbook(workbook);
  	}

	/**
	 * create first sheet and first row
	 */
	private void initialWorkbook(Workbook workbook) {
		LOGGER.info("[excel][initial][sheet 0][add]");
		Sheet  sheet = workbook.createSheet(SHEET_NAME);
 		LOGGER.info("[excel][initial][row 0][add]");
		Row firstRow = sheet.createRow(ROW_NUMBER++);

		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement
				.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			String cellValue = caseElements.get(i).toString();
			firstRow.createCell(i).setCellValue(cellValue);
			LOGGER.info(String.format(
					"[excel][initial][row 0][cell %d][set] value:%s", i, cellValue));
		}
	}

	public void add(TestCaseWrapper testCaseWrapper) {
		Sheet sheetForTestCase = workbook.getSheetAt(0);
		Row caseRow = sheetForTestCase.createRow(ROW_NUMBER++);
		LOGGER.debug(String.format("[excel][process][row %d][add]", ROW_NUMBER));

		TestCaseWrapperStringFormatter testCaseWrapperStringFormatter = new TestCaseWrapperStringFormatter(
				testCaseWrapper);
		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement
				.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			String caseElementValue = testCaseWrapperStringFormatter
					.format(caseElements.get(i));
			Cell cellForCase = caseRow.createCell(i);
			cellForCase.setCellValue(caseElementValue);
			LOGGER.debug(String.format(
					"[excel][process][row %d][cell %d][set] value:%s",
					ROW_NUMBER, i, caseElementValue));
		}
	}

	public void generateExcelFile() {
		FileOutputStream fileOutputStream = null;
		try {
			File file=new File(folder);
			if(!file.exists()){
				LOGGER.info(String.format("[excel]excel file's folder [%s] hasn't exist, to create it",folder));
 				file.mkdirs();
 			}

			String outputFullPath = folder+"\\"+this.file;
			LOGGER.info(String.format("[excel][output] excel file path: %s",outputFullPath));

			fileOutputStream = new FileOutputStream(outputFullPath);
			writeOutputStreamToWorkbook(fileOutputStream);
  		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					LOGGER.warn(e.getMessage(), e);
				}
		}
 	}

 	protected void writeOutputStreamToWorkbook(FileOutputStream os)
			throws IOException {
		workbook.write(os);
	}

 }

