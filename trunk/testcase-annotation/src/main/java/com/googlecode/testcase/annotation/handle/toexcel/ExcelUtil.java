package com.googlecode.testcase.annotation.handle.toexcel;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import com.googlecode.testcase.annotation.handle.toexcel.strategy.ToHtmlWithExcel;

public class ExcelUtil {

	private static final Logger LOGGER = Logger.getLogger(ExcelUtil.class);

	private ExcelUtil() {
		// no instance
	}

	public static void convertExcelToHmtl(String outputFullPathForExcel, String outputFullPathForHtml) {
		try {
			LOGGER.info(String.format("[excel][result][output] html file(with excel content) path: %s", outputFullPathForHtml));
			ToHtmlWithExcel create = ToHtmlWithExcel.create(outputFullPathForExcel,outputFullPathForHtml);
			create.setCompleteHTML(true);
 			create.print();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void writeExcelFile(String outputFullPath, Workbook workbook) {
		FileOutputStream fileOutputStream = null;
		try {
			LOGGER.info(String.format("[excel][result][output] excel file path: %s", outputFullPath));

			fileOutputStream = new FileOutputStream(outputFullPath);
			workbook.write(fileOutputStream);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (fileOutputStream != null)
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					LOGGER.warn(e.getMessage(), e);
				}
		}
	}

}
