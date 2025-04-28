package ru.effectivemobile.task_management_system.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TaskControllerAspect {

    @Pointcut("execution(* ru.effectivemobile.task_management_system.controller.TaskController.*(..))")
    public void taskControllerMethods() {}

    @Before("taskControllerMethods()")
    public void beforeTaskControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        log.info("TaskController.{}: invoke method with arguments: {}", methodName, args);
    }

    @AfterReturning(pointcut = "taskControllerMethods()", returning = "result")
    public void afterReturningTaskControllerMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.debug("TaskController.{}: finish method with result: {}", methodName, result);
    }

    @AfterThrowing(pointcut = "taskControllerMethods()", throwing = "exception")
    public void afterThrowingTaskControllerMethod(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.error("TaskController.{}: throw {}", methodName, exception.getMessage(), exception);
    }
}
