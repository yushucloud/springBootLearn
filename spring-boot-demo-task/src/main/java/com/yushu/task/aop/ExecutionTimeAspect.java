package com.yushu.task.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    @Around("@annotation(com.yushu.task.aop.TrackExecutionTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object proceed = joinPoint.proceed(); // 执行目标方法

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("方法：{}执行时间：{} ms", joinPoint.getSignature().getDeclaringTypeName(), executionTime);

        return proceed;
    }
}
