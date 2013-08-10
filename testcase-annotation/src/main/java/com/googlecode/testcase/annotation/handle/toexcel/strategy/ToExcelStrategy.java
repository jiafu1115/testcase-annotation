package com.googlecode.testcase.annotation.handle.toexcel.strategy;

import java.util.List;

import com.googlecode.testcase.annotation.wrapper.TestCaseWrapper;

public interface ToExcelStrategy {
	public void add(TestCaseWrapper testCaseWrapper);
	public void generateFile();
	public List<TestCaseWrapper> getTestCaseWrapperList();
}
