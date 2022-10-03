package com.atwj.yygh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-01 8:37
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.atwj")
@MapperScan("com.atwj.yygh.mapper")
public class ServiceCmnMain8202 {
    public static void main(String[] args){
        SpringApplication.run(ServiceCmnMain8202.class, args);
    }
}
