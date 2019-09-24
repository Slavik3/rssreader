package com.edvantis.rssreader.annotation;

import java.lang.annotation.*;


@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
	 String executionTime() default "";
}
