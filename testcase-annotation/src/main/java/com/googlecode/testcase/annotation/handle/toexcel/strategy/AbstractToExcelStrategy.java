package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;


public abstract class AbstractToExcelStrategy implements ToExcelStrategy{

	private static final Logger LOGGER=Logger.getLogger(AbstractToExcelStrategy.class);

	protected String folder;
	protected String file;

	public AbstractToExcelStrategy(String folder,String file) {
		super();
		this.folder=folder;
		this.file=file;
  	}

 	public void generateExcelFile() {
		FileOutputStream fileOutputStream = null;
		try {
			File file=new File(folder);
			if(!file.exists()){
				LOGGER.info(String.format("[excel]excel file's folder [%s] hasn't exist, to create it",folder));
 				file.mkdirs();
 			}

			String outputFullPath = folder+"\\"+this.file;
			LOGGER.info(String.format("[excel]output excel file path:%s",outputFullPath));

			fileOutputStream = new FileOutputStream(outputFullPath);
			writeOutputStreamToWorkbook(fileOutputStream);
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

 	protected abstract void writeOutputStreamToWorkbook(FileOutputStream os) throws IOException;

}

