package com.googlecode.testcase.annotation;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ToExcel {

	void process() throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("test cases");

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);

		cell.setCellValue("hello world");

		row = sheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellValue(new Date());

		FileOutputStream os = new FileOutputStream("c:\\workbook.xls");
		workbook.write(os);
		os.close();

	}

	public static void main(String[] args) throws Exception{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("test cases");

		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);

		cell.setCellValue("hello \nworldhellow world");

		HSSFCell createCell = row.createCell(1);
		createCell.setCellValue("step1: do one step\n step2: do step2");

		row = sheet.createRow(1);

		cell = row.createCell(0);
		cell.setCellValue(new Date());

		FileOutputStream os = new FileOutputStream("c:\\workbook.xls");
		workbook.write(os);
		os.close();
	}

}
