package com.atwj.yygh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-16 17:23
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.atwj"})
public class StatisticsService8208 {
    public static void main(String[] args) {
        SpringApplication.run(StatisticsService8208.class, args);
    }
}
