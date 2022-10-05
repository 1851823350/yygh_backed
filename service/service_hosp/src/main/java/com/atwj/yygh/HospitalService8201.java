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
 * @create 2022-09-27 8:31
 */
@SpringBootApplication
@MapperScan("com.atwj.yygh.mapper")
@ComponentScan(basePackages = "com.atwj") //扫描工程项目下com/atwj下的内容
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.atwj")
public class HospitalService8201 {
    public static void main(String[] args){
        SpringApplication.run(HospitalService8201.class, args);
    }
}
