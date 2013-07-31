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

	public static ExcelType fromString(String type){
		if(type==null)
			throw new IllegalArgumentException("type shouldn't be null");

		type = formtType(type);
		ExcelType[] values = ExcelType.values();
		for(ExcelType excelType: values){
			if(excelType.toString().equalsIgnoreCase(type))
				return excelType;
		}

		return null;
 	}
	private static String formtType(String type) {
		type=type.trim();
		if(!type.startsWith("."));
			type="."+type;
		return type;
	}

	public String toString(){
  		return extension;
 	}

}
