package com.edvantis.rssreader.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeTrackerAdvice {

	Logger logger = LoggerFactory.getLogger(ExecutionTimeTrackerAdvice.class);

	@Around("@annotation(com.edvantis.rssreader.annotation.LogExecutionTime)")
	public Object trackTime(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
		Object obj = pjp.proceed();
		long endTime = System.currentTimeMillis();
		logger.info("Method name" + pjp.getSignature() + "time taken to execute" + (endTime - startTime));
		return obj;
	}

}
