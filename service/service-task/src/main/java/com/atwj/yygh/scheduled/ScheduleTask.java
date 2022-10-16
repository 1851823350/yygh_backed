package com.atwj.yygh.scheduled;

import com.atwj.yygh.constant.MqConst;
import com.atwj.yygh.service.RabbitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-16 15:01
 */
@Component
@EnableScheduling
public class ScheduleTask {
    @Autowired
    private RabbitService rabbitService;

    //每天8点执行方法，就医提醒
    //cron表达式，设置执行间隔，可网上查询
    //0 0 8 * * ?
    @Scheduled(cron = "0/30 * * * * ?")
    public void taskPatient() {
        rabbitService.sendMessage(MqConst.EXCHANGE_DIRECT_TASK,MqConst.ROUTING_TASK_8,"");
    }
}
