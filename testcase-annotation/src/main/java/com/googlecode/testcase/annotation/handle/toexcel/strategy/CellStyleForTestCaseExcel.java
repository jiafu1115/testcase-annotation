package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

public class CellStyleForTestCaseExcel {


	private CellStyle cellStyleForOtherRowsWithTextWrap;
	private CellStyle cellStyleForOtherRowsWithoutTextWrap;
	private CellStyle cellStyleForFirstRow;


	public CellStyleForTestCaseExcel(Workbook workbook) {
		super();
		this.cellStyleForFirstRow = getCellStyleForFirstRow(workbook);
		this.cellStyleForOtherRowsWithTextWrap = getCellStyleForOtherCaseRows(workbook);
		cellStyleForOtherRowsWithTextWrap.setWrapText(true);
		this.cellStyleForOtherRowsWithoutTextWrap = getCellStyleForOtherCaseRows(workbook);
		cellStyleForOtherRowsWithoutTextWrap.setWrapText(false);

	}

	private CellStyle getCellStyleForOtherCaseRows(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);

		Font createFont = workbook.createFont();
		createFont.setBoldweight((short) 300);
		cellStyle.setFont(createFont);

		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);

		return cellStyle;
	}

	private CellStyle getCellStyleForFirstRow(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();

		cellStyle.setFillForegroundColor((short) 13);
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

		Font createFont = workbook.createFont();
		createFont.setBoldweight((short) 600);
		cellStyle.setFont(createFont);

		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);

		return cellStyle;
	}

	public CellStyle getCellStyleForOtherRowsWithTextWrap() {
		return cellStyleForOtherRowsWithTextWrap;
	}

	public CellStyle getCellStyleForOtherRowsWithoutTextWrap() {
		return cellStyleForOtherRowsWithoutTextWrap;
	}

	public CellStyle getCellStyleForFirstRow() {
		return cellStyleForFirstRow;
	}


}
