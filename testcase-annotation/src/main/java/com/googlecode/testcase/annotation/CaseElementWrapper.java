package com.googlecode.testcase.annotation;

import java.util.Arrays;
import java.util.List;

public enum CaseElementWrapper {

	ID, TITLE, PRECONDITIONS, STEPS, RESULTS, METHOD;

	public String toString() {
		return super.toString().toLowerCase();
	}

	public static List<CaseElementWrapper> toListAsSequence() {
		return Arrays.asList(CaseElementWrapper.values());
	}
}
