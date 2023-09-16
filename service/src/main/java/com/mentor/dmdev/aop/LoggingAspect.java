package com.mentor.dmdev.aop;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }

    @Before("isServiceLayer()")
    public void addLoggingBefore(JoinPoint joinPoint) {
        String methodName = getCalledMethodName(joinPoint);
        log.info("Вызов метода: {} c параметром(-ами): {} ", methodName, Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(value = "isServiceLayer()", returning = "result")
    public void addLoggingAfter(JoinPoint joinPoint, Object result) {
        String methodName = getCalledMethodName(joinPoint);
        log.info("Вызов метода: {} вернул значение {} ", methodName, result);
    }

    @NonNull
    private String getCalledMethodName(@NonNull JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String declaringTypeName = signature.getDeclaringTypeName();
        String name = signature.getName();
        return declaringTypeName + "." + name;
    }
}