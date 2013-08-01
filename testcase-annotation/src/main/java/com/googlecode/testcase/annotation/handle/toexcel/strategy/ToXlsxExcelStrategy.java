package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ToXlsxExcelStrategy extends AbstractToExcelStrategy {

 	public ToXlsxExcelStrategy(String folder, String file) {
		super(folder, file, new XSSFWorkbook ());
	}

}
