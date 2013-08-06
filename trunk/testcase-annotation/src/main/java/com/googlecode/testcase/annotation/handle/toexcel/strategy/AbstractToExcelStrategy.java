package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.googlecode.testcase.annotation.handle.toexcel.ExcelConstants;
import com.googlecode.testcase.annotation.handle.toexcel.ExcelType;
import com.googlecode.testcase.annotation.handle.toexcel.ExcelUtil;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper.TestCaseWrapperElement;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapperStringFormatter;

public abstract class AbstractToExcelStrategy implements ToExcelStrategy {

	public static final Logger LOGGER = Logger
			.getLogger(AbstractToExcelStrategy.class);

	protected String folder;
	protected String file;
	protected Workbook workbook;
	private List<TestCaseWrapper> testCaseWrapperList;

	public AbstractToExcelStrategy(String folder, String file, Workbook workbook) {
		super();
		this.folder = folder;
		this.file = file;
		this.workbook = workbook;
		this.testCaseWrapperList=new ArrayList<TestCaseWrapper>();
	}

	public void add(TestCaseWrapper testCaseWrapper) {
		testCaseWrapperList.add(testCaseWrapper);
	}

	private void processTestCaseWrapper(TestCaseWrapper testCaseWrapper) {
		String title = testCaseWrapper.getTestCase().title();
		LOGGER.info(String.format("[excel][process][add test case][begin] %s",
				title));
		Sheet sheet = createNewSheetIfNeed(testCaseWrapper);

		int newRowNum = sheet.getLastRowNum() + 1;
		Row caseRow = sheet.createRow(newRowNum);
		if (LOGGER.isDebugEnabled())
			LOGGER.debug(String.format("[excel][process][row %d][add]",
					newRowNum));

		TestCaseWrapperStringFormatter testCaseWrapperStringFormatter = new TestCaseWrapperStringFormatter(
				testCaseWrapper);
		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement
				.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			TestCaseWrapperElement testCaseWrapperElement = caseElements.get(i);
			String caseElementValue = testCaseWrapperStringFormatter
					.format(testCaseWrapperElement);
			Cell cellForCase = caseRow.createCell(i);
			cellForCase.setCellValue(caseElementValue);

			CellStyle cellStyleForCaseRow = getCellStyleForCaseRow();
			cellStyleForCaseRow.setWrapText(testCaseWrapperElement==TestCaseWrapperElement.TITLE);
			cellForCase.setCellStyle(cellStyleForCaseRow);

			if(testCaseWrapperElement==TestCaseWrapperElement.METHOD){
				sheet.autoSizeColumn(i);
			}

			if (LOGGER.isDebugEnabled())
				LOGGER.debug(String.format(
						"[excel][process][row %d][cell %d][set] value:%s",
						newRowNum, i, caseElementValue));
		}

		LOGGER.info(String.format("[excel][process][add test case][end] %s",
				title));
	}

	private CellStyle getCellStyleForCaseRow() {
		CellStyle cellStyle = workbook.createCellStyle();
 		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		return cellStyle;
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

		CellStyle cellStyle = getCellFormatForFirstRow();
		firstRow.setRowStyle(cellStyle);
		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement
				.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			TestCaseWrapperElement testCaseWrapperElement = caseElements.get(i);
			String cellValue = testCaseWrapperElement.toString();
			Cell cell = firstRow.createCell(i);
			cell.setCellValue(cellValue);

 			applyCellStyle(sheet, cellStyle, i, testCaseWrapperElement, cell);

			if (LOGGER.isDebugEnabled())
				LOGGER.info(String.format(
						"[excel][initial][row 0][cell %d][set] value:%s", i,
						cellValue));
		}

		return sheet;
	}

	private void applyCellStyle(Sheet sheet, CellStyle cellStyle, int i,
			TestCaseWrapperElement testCaseWrapperElement, Cell cell) {
		cell.setCellStyle(cellStyle);
		sheet.setColumnWidth(i, TestCaseWrapperStringFormatter.getColumnWidth(testCaseWrapperElement));
	}

	private CellStyle getCellFormatForFirstRow() {
		CellStyle cellStyle = this.workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);

        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor((short) 13);
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        return cellStyle;
	}

	private String getSheetName(TestCaseWrapper testCaseWrapper) {
		String moduleName = testCaseWrapper.getModuleName();
		String sheetName = moduleName.isEmpty() ? ExcelConstants.DEFAULT_SHEET_NAME_WHEN_MORE_SHEET_EXIST
				: moduleName;
		return sheetName;
	}

 	public void generateFile() {
 		Collections.sort(testCaseWrapperList);

 		Iterator<TestCaseWrapper> iterator = testCaseWrapperList.iterator();
		while(iterator.hasNext()){
			TestCaseWrapper next = iterator.next();
			processTestCaseWrapper(next);
			iterator.remove();
 		}

		changeSheetNameIfNeed();
		File fileForFolder = new File(folder);
		if (!fileForFolder.exists()) {
			LOGGER.info(String
					.format("[excel]excel file's folder [%s] hasn't existed, to create it",
							folder));
			fileForFolder.mkdirs();
		}

		String separator = folder.endsWith(ExcelConstants.FILE_SEPARATOR)?"":ExcelConstants.FILE_SEPARATOR;
		String outputFullPathForExcel = folder +separator + file;
		String outputFullPathForHtml = getOutputFullPathForHtml(outputFullPathForExcel);

 		ExcelUtil.writeExcelFile(outputFullPathForExcel,workbook);
		ExcelUtil.convertExcelToHmtl(outputFullPathForExcel,outputFullPathForHtml);
 	}

	private String getOutputFullPathForHtml(String outputFullPathForExcel) {
		String outputFullPathForHtmlBeforeExtension;
		if(outputFullPathForExcel.endsWith(ExcelType.XLS.getExtension())){
			outputFullPathForHtmlBeforeExtension=outputFullPathForExcel.substring(0, outputFullPathForExcel.length()-ExcelType.XLS.getExtension().length());
		}else{
			outputFullPathForHtmlBeforeExtension=outputFullPathForExcel.substring(0, outputFullPathForExcel.length()-ExcelType.XLSX.getExtension().length());
		}
		String outputFullPathForHtml=outputFullPathForHtmlBeforeExtension+".html";

		return outputFullPathForHtml;
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


	@Override
	public String toString() {
		return "AbstractToExcelStrategy [folder=" + folder + ", file=" + file
				+ "]";
	}

}
