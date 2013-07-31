package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper.TestCaseWrapperElement;
import com.googlecode.testcase.annotation.wrapper.TestCaseWrapperStringFormatter;

public class ToXslExcelStrategy extends AbstractToExcelStrategy {

	private static final Logger LOGGER = Logger
			.getLogger(ToXslExcelStrategy.class);

	private static final String SHEET_NAME = "Test Cases";
	private static int ROW_NUMBER = 0;

	private HSSFWorkbook workbook;

	public ToXslExcelStrategy(String folder, String file) {
		super(folder, file);
		this.workbook = new HSSFWorkbook();
		initialWorkbook();
	}

	public void initialWorkbook() {
		HSSFSheet sheet = workbook.createSheet(SHEET_NAME);

		LOGGER.info("[excel][initial][row 0][add]");
		HSSFRow caseElementRow = sheet.createRow(ROW_NUMBER++);

		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement
				.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			String cellValue = caseElements.get(i).toString();
			caseElementRow.createCell(i).setCellValue(cellValue);
			LOGGER.info(String.format(
					"[excel][initial][row 0][cell %d][set] value:%s", i, cellValue));
		}
	}

	public void add(TestCaseWrapper testCaseWrapper) {
		HSSFSheet sheetForTestCase = workbook.getSheetAt(0);
		HSSFRow caseRow = sheetForTestCase.createRow(ROW_NUMBER++);
		LOGGER.debug(String.format("[excel][process][row %d][add]", ROW_NUMBER));

		TestCaseWrapperStringFormatter testCaseWrapperStringFormatter = new TestCaseWrapperStringFormatter(
				testCaseWrapper);
		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement
				.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			String caseValue = testCaseWrapperStringFormatter
					.format(caseElements.get(i));
			HSSFCell caseCell = caseRow.createCell(i);
			caseCell.setCellValue(caseValue);
			LOGGER.debug(String.format(
					"[excel][process][row %d][cell %d][set] value:%s",
					ROW_NUMBER, i, caseValue));
		}
	}

	@Override
	protected void writeOutputStreamToWorkbook(FileOutputStream os)
			throws IOException {
		workbook.write(os);
	}

}
