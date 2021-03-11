package com.enjoyu.admin.common.datasource;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NamingDataSource {
    String value() default "";
}
