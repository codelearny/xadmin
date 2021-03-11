package com.enjoyu.admin.common.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
public class NamingDynamicDataSourceExchange {
    @Pointcut("@annotation(com.enjoyu.admin.common.datasource.NamingDataSource)")
    public void ann() {
    }

    @Before("ann() && @annotation(namingDataSource)")
    public void before(JoinPoint joinPoint, NamingDataSource namingDataSource) {
        String name = namingDataSource.value();
        NamingDynamicDataSource.NamingDataSourceHolder.setDataSourceName(name);
    }
}
