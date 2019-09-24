package com.edvantis.rssreader.annotation;

import java.lang.annotation.*;

import org.springframework.stereotype.Component;

@Component
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogExecutionTime {
	
}
