package com.googlecode.testcase.annotation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.googlecode.testcase.annotation.TestCaseWrapper.TestCaseWrapperElement;

public class ToExcelHandle {

	private static final String SHEET_NAME = "Test Cases";
	private static int ROW_NUMBER = 0;

	private HSSFWorkbook workbook;
	private String folder;
	private String file;

	public ToExcelHandle(String folder,String file) {
		super();
		this.folder=folder;
		this.file=file;
 		this.workbook = new HSSFWorkbook();
		initialWorkbook();
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
			File file=new File(folder);
			if(!file.exists())
				file.mkdirs();

			os = new FileOutputStream(folder+"\\"+this.file);
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

	public static void main(String args[]){
		File file=new File("C:\\com.googlecode\\target\\generated-sources\\apt\\");
		System.out.println(file.exists());
		file.mkdirs();
	}

}
