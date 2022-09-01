package com.enjoyu.admin.component.mybatis;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis配置类
 *
 * @author enjoyu
 */
@ConditionalOnProperty(prefix = "x-admin.mybatis.enable", value = "true")
@Configuration
@MapperScan("com.enjoyu.admin.component.mybatis.mapper")
public class MyBatisConfig {
}
