package com.googlecode.testcase.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestCase {

	String module() default "";
 	String id() default "";
	String title();
	String[] preConditions() default "";
	String[] steps();
	String[] results();

}
