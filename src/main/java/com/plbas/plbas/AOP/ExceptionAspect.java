package com.plbas.plbas.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class ExceptionAspect {

    @Pointcut("execution(* com.plbas.plbas.service.Impl.*.*(..))")
    public void Exceptions(){}

    @AfterThrowing(value = "Exceptions()",throwing = "exception")
    public void ExceptionLogger(JoinPoint joinPoint,Exception exception)
    {
        String name=joinPoint.getSignature().getName();
        String msg=exception.getMessage();
        log.error("方法：[{}] 发生了错误：{}",name,msg);
    }

}
