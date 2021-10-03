package com.enjoyu.admin.component.datasource;

import java.lang.annotation.*;

/**
 * 命名数据源注解
 *
 * @author enjoyu
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NamingDataSource {
    String value() default "";
}
