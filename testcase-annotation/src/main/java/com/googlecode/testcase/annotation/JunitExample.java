package com.googlecode.testcase.annotation;

import org.junit.Test;

public class JunitExample {


	@TestCase(
			id = 0,
		 	title = "case title",
			steps = {
					"step one",
					"step two"
					},
			results = {
					"result one",
					"result two"
					 }
 			)
	@Test
	public void test() {

 	}

}
