package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ToXlsExcelStrategy extends AbstractToExcelStrategy {

 	public ToXlsExcelStrategy(String folder, String file) {
		super(folder, file, new HSSFWorkbook ());
	}

}
