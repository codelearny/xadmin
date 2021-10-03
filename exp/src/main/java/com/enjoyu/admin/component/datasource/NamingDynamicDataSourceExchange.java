package com.enjoyu.admin.component.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 命名数据源注解切面
 *
 * @author enjoyu
 */
@Aspect
public class NamingDynamicDataSourceExchange {
    @Pointcut("@annotation(com.enjoyu.admin.component.datasource.NamingDataSource)")
    public void ann() {
    }

    @Before("ann() && @annotation(namingDataSource)")
    public void before(JoinPoint joinPoint, NamingDataSource namingDataSource) {
        String name = namingDataSource.value();
        NamingDynamicDataSource.NamingDataSourceHolder.setDataSourceName(name);
    }
}
