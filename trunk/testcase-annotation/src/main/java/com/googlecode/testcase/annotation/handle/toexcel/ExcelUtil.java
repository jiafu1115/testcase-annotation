package com.googlecode.testcase.annotation.handle.toexcel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;


public class ExcelUtil {

	private static final Logger LOGGER = Logger
			.getLogger(ExcelUtil.class);


	private ExcelUtil(){
		//no instance
	}


	public static void writeExcelFile(String folder,String file, Workbook workbook) {
		FileOutputStream fileOutputStream = null;
		try {
			File fileForFolder = new File(folder);
			if (!fileForFolder.exists()) {
				LOGGER.info(String
						.format("[excel]excel file's folder [%s] hasn't existed, to create it",
								folder));
				fileForFolder.mkdirs();
			}

			String separator = folder.endsWith(ExcelConstants.FILE_SEPARATOR)?"":ExcelConstants.FILE_SEPARATOR;
			String outputFullPath = folder +separator + file;
			LOGGER.info(String.format(
					"[excel][result][output] excel file path: %s",
					outputFullPath));

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
