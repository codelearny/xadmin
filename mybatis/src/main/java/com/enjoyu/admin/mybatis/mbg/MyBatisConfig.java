package com.enjoyu.admin.mybatis.mbg;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.enjoyu.admin.mybatis.mapper")
public class MyBatisConfig {
}
