package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

import com.googlecode.testcase.annotation.handle.toexcel.ExcelConstants;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper.TestCaseWrapperElement;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapperStringFormatter;

public abstract class AbstractToExcelStrategy implements ToExcelStrategy {

	private static final Logger LOGGER = Logger
			.getLogger(AbstractToExcelStrategy.class);

	protected String folder;
	protected String file;
	protected Workbook workbook;

	public AbstractToExcelStrategy(String folder, String file, Workbook workbook) {
		super();
		this.folder = folder;
		this.file = file;
		this.workbook = workbook;
	}

	public void add(TestCaseWrapper testCaseWrapper) {
		String title = testCaseWrapper.getTestCase().title();
		LOGGER.info(String.format("[excel][process][add test case][begin] %s",
				title));
		Sheet sheet = createNewSheetIfNeed(testCaseWrapper);

		int newRowNum = sheet.getLastRowNum() + 1;
		Row caseRow = sheet.createRow(newRowNum);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug(String.format("[excel][process][row %d][add]",
					newRowNum));

		CellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setFillPattern(XSSFCellStyle.FINE_DOTS );
		cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		cellStyle.setFillBackgroundColor(IndexedColors.RED.getIndex());

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
			cellForCase.setCellStyle(cellStyle);
			if (LOGGER.isDebugEnabled())
				LOGGER.debug(String.format(
						"[excel][process][row %d][cell %d][set] value:%s",
						newRowNum, i, caseElementValue));
		}

		LOGGER.info(String.format("[excel][process][add test case][end] %s",
				title));
	}

	private Sheet createNewSheetIfNeed(TestCaseWrapper testCaseWrapper) {
		String sheetName = getSheetName(testCaseWrapper);
		Sheet sheet = workbook.getSheet(sheetName);
		if (sheet == null) {
			sheet = createAndInitailSheet(sheetName);
		}
		return sheet;
	}

	/**
	 * create first sheet and first row
	 */
	private Sheet createAndInitailSheet(String sheetName) {
		LOGGER.info("[excel][initial]need create sheet:"+sheetName);
		int newSheetNumber = workbook.getNumberOfSheets()+1;
		LOGGER.info(String.format("[excel][initial][sheet %d][add] sheet:%s",newSheetNumber, sheetName));
  		Sheet sheet = workbook.createSheet(sheetName);
		LOGGER.info(String.format("[excel][initial][sheet %d][row 0][add]", newSheetNumber));
		Row firstRow = sheet.createRow(0);

		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement
				.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			String cellValue = caseElements.get(i).toString();
			firstRow.createCell(i).setCellValue(cellValue);
			if (LOGGER.isDebugEnabled())
				LOGGER.info(String.format(
						"[excel][initial][row 0][cell %d][set] value:%s", i,
						cellValue));
		}

		return sheet;
	}

	private String getSheetName(TestCaseWrapper testCaseWrapper) {
		String moduleName = testCaseWrapper.getModuleName();
		String sheetName = moduleName.isEmpty() ? ExcelConstants.DEFAULT_SHEET_NAME_WHEN_MORE_SHEET_EXIST
				: moduleName;
		return sheetName;
	}

	public void generateExcelFile() {
		changeSheetNameIfNeed();
		writeExcelFile();
	}

	private void changeSheetNameIfNeed() {
		int numberOfSheets = workbook.getNumberOfSheets();
		Sheet sheet = workbook
				.getSheet(ExcelConstants.DEFAULT_SHEET_NAME_WHEN_MORE_SHEET_EXIST);
		if (numberOfSheets == 1 && sheet != null) {
			workbook.setSheetName(
					workbook.getSheetIndex(ExcelConstants.DEFAULT_SHEET_NAME_WHEN_MORE_SHEET_EXIST),
					ExcelConstants.DEFAULT_SHEET_NAME_WHEN_ONLY_DEFAULT_MODULE_EXIST);
			LOGGER.info(String.format("[excel][result][update]change sheet name from %s to %s when only one module undefined name", ExcelConstants.DEFAULT_SHEET_NAME_WHEN_MORE_SHEET_EXIST,ExcelConstants.DEFAULT_SHEET_NAME_WHEN_ONLY_DEFAULT_MODULE_EXIST));
		}
	}

	private void writeExcelFile() {
		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(folder);
			if (!file.exists()) {
				LOGGER.info(String
						.format("[excel]excel file's folder [%s] hasn't exist, to create it",
								folder));
				file.mkdirs();
			}

 			String outputFullPath = folder + "\\" + this.file;
			LOGGER.info(String.format(
					"[excel][result][output] excel file path: %s",
					outputFullPath));

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

	@Override
	public String toString() {
		return "AbstractToExcelStrategy [folder=" + folder + ", file=" + file
				+ "]";
	}

}
