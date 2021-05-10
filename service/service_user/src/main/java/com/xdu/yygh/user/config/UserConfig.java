package com.xdu.yygh.user.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.xdu.yygh.user.mapper")
public class UserConfig {
}
