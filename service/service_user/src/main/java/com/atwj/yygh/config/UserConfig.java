package com.atwj.yygh.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-09 12:12
 */
@Configuration
@MapperScan("com.atwj.yygh.mapper")
public class UserConfig {
}
