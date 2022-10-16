package com.atwj.yygh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-16 14:51
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//取消数据源自动配置
@EnableDiscoveryClient
public class TaskService8207 {
    public static void main(String[] args) {
        SpringApplication.run(TaskService8207.class, args);
    }
}
