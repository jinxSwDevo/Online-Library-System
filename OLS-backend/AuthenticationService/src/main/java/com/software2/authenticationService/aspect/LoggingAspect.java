package com.software2.authenticationService.aspect;

import org.slf4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
     private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.software2.authenticationService.*.*(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        log.info("PerformanceMonitoringAspect: Method {} executed in {} milliseconds", joinPoint.getSignature(), executionTime);
        return result;
    }
    
    @Pointcut("execution(* com.software2.authenticationService.Repository..*(..))")
    public void forRepositoryLog() {}

    @Pointcut("execution(* com.software2.authenticationService.Service..*(..))")
    public void forServiceLog() {}

    @Pointcut("execution(* com.software2.authenticationService.Controller..*(..))")
    public void forControllerLog() {}

   @Pointcut(value = "forRepositoryLog() || forServiceLog() || forControllerLog()")
    public void forAllApp() {}

    @Before("forAllApp()")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("====> Method Name is >> {}", methodName);

        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("===> Argument >> {}", arg);
        }
    }
}