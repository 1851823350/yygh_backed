package com.atwj.yygh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-09 10:54
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "com.atwj")
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.atwj")
@MapperScan("com.atwj.yygh.mapper")
public class UserService8150 {
    public static void main(String[] args){
        SpringApplication.run(UserService8150.class, args);
    }
}
