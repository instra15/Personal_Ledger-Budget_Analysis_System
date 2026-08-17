package com.plbas.plbas.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class MonitorAspect {

    @Pointcut("execution(* com.plbas.plbas.service.Impl.*.*(..))")
    public void Monitor(){}

    @Around(value = "Monitor()")
    public Object timeMonitor(ProceedingJoinPoint proceedingJoinPoint) throws Throwable
    {
        Long start = System.nanoTime();

        Object result = proceedingJoinPoint.proceed();

        Long end = System.nanoTime();
        String methodName=proceedingJoinPoint.getSignature().getName();
        long duration=end-start;
        log.info("方法 [{}] 执行耗时: {} ms", methodName, duration / 1_000_000);
        return result;
    }

}
