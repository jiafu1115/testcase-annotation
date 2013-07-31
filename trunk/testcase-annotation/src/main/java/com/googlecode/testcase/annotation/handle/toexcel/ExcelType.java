package com.googlecode.testcase.annotation.handle.toexcel;

public enum ExcelType {
	XLS(".xls"), XLSX(".xlsx");

	private String extension;
	private ExcelType(String extension){
		this.extension=extension;
	}
	public String getExtension(){
		return extension;
 	}

}
