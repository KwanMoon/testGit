package com.kgs7276.board.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SampleAdvice {

  private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);

  @Before("execution(* com.kgs7276.board.service.MessageService*.*(..))")
  public void startLog(JoinPoint jp) {

    logger.info("---------이것은 @Before 시작--------");
    logger.info(Arrays.toString(jp.getArgs()));
    logger.info("---------이것은 @Before 끝  --------");

  }
  
  
  @Around("execution(* com.kgs7276.board.service.MessageService*.*(..))")
  public Object timeLog(ProceedingJoinPoint pjp)throws Throwable{
    
	logger.info("---------이것은 @Around 시작--------");  
	  
    long startTime = System.currentTimeMillis();
    logger.info(Arrays.toString(pjp.getArgs()));
    
    Object result = pjp.proceed();
    
    long endTime = System.currentTimeMillis();
    logger.info( pjp.getSignature().getName()+ " : " + (endTime - startTime) );    
    
    logger.info("---------이것은 @Around 끝--------");
    
    return result;
  }   
  
  
  @After("execution(* com.kgs7276.board.service.MessageService*.*(..))")
  public void endLog() {

    logger.info("---------이것은 @After 시작--------");
    logger.info("---------이것은 @After 끝  --------");

  }

}