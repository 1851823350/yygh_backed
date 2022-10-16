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
 * @create 2022-10-15 10:56
 */
@SpringBootApplication
@MapperScan("com.atwj.yygh.mapper")
@ComponentScan(basePackages = {"com.atwj"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.atwj"})
public class OrderService8206 {
    public static void main(String[] args) {
        SpringApplication.run(OrderService8206.class, args);
    }
}
