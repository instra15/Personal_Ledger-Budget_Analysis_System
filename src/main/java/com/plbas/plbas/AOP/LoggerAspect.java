package com.plbas.plbas.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class LoggerAspect {

    @Pointcut("execution(* com.plbas.plbas.service.Impl.*.*(..))")
    public void Logger(){}

    @Before("Logger()")
    public void BeforeLogger(JoinPoint joinPoint)
    {
        String name=joinPoint.getSignature().getName();
        log.info("执行了：[{}]",name);
    }

    @AfterReturning(value = "Logger()",returning = "result")
    public void AfterReturningLogger(JoinPoint joinPoint,Object result)
    {
        log.info("返回值为: {}",result);
    }






}
