package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;

public interface ToExcelStrategy {
	public void add(TestCaseWrapper testCaseWrapper);
	public void generateExcelFile();
}
