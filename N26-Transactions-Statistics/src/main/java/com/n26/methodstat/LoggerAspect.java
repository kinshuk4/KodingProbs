package com.n26.methodstat;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggerAspect {

    @Around("execution(* *(..)) && @annotation(com.n26.methodstat.MethodStats)")
    public Object log(ProceedingJoinPoint point) throws Throwable
    {
        long start = System.currentTimeMillis();
        Object result = point.proceed();
        log.debug("className={}, methodName={}, timeMs={},threadId={}, args={}",
                point.getSignature().getDeclaringTypeName(),
                MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
                System.currentTimeMillis() - start,
                Thread.currentThread().getId(),
                Arrays.toString(point.getArgs())
        );
        return result;
    }
}
