package com.googlecode.testcase.annotation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.googlecode.testcase.annotation.TestCaseWrapper.TestCaseWrapperElement;

public class ToExcelHandle {

	private static final String SHEET_NAME = "Test Cases";
	private static int ROW_NUMBER = 0;
	private String excelPath;
	private HSSFWorkbook workbook;

	public ToExcelHandle(String excelPath) {
		super();
		this.excelPath = excelPath;
		this.workbook = new HSSFWorkbook();
		initialWorkbook();
	}

	public ToExcelHandle() {
 		this(generateExcelPath());
	}

	private static String generateExcelPath(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String date= simpleDateFormat.format(new Date());
		return date+".xsl";
	}

	public void initialWorkbook() {
		HSSFSheet sheet = workbook.createSheet(SHEET_NAME);
		HSSFRow caseElementRow = sheet.createRow(ROW_NUMBER++);

		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			caseElementRow.createCell(i).setCellValue(caseElements.get(i).toString());
		}
  	}

	public void add(TestCaseWrapper testCaseWrapper) {
		HSSFSheet sheetForTestCase = workbook.getSheetAt(0);
		HSSFRow caseRow = sheetForTestCase.createRow(ROW_NUMBER++);

 		TestCaseWrapperStringFormatter testCaseWrapperStringFormatter = new TestCaseWrapperStringFormatter(testCaseWrapper);
		List<TestCaseWrapperElement> caseElements = TestCaseWrapperElement.toListAsSequence();
		int size = caseElements.size();
		for (int i = 0; i < size; i++) {
			String caseColumnValue = testCaseWrapperStringFormatter.format(caseElements.get(i));
			HSSFCell caseColumn = caseRow.createCell(i);
			caseColumn.setCellValue(caseColumnValue);
		}
 	}

	public void generate() {
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(excelPath);
			workbook.write(os);
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (os != null)
				try {
					os.close();
				} catch (IOException e) {
				}
		}
	}

}
