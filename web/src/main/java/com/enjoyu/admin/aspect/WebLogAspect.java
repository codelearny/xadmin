package com.enjoyu.admin.aspect;

import com.enjoyu.admin.utils.WebIPUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

    @Pointcut("execution(public * com.enjoyu.admin.web.controller.*.*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("\n>>请求地址 : {}\n>>IP : {}\n>>HTTP METHOD : {}\n>>CLASS_METHOD : {}.{}\n>>参数 : {}",
                request.getRequestURL().toString(),
                WebIPUtil.getIpAddress(request),
                request.getMethod(),
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));

    }

    @AfterReturning(returning = "ret", pointcut = "logPointCut()")
    public void doAfterReturning(Object ret) {
        log.info("返回值 : {}", ret);
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Instant start = Instant.now();
        Object ob = pjp.proceed();// ob 为方法的返回值
        Instant end = Instant.now();
        log.info("耗时 : {}", Duration.between(start, end).getSeconds());
        return ob;
    }
}
