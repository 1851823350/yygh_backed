package com.atwj.yygh.controller;


import com.atwj.yygh.OrderFeignClient;
import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.vo.order.OrderCountQueryVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/admin/statistics")
public class StatisticsController {

    @Resource
    private OrderFeignClient orderFeignClient;

    //获取预约统计数据
    @GetMapping("getCountMap")
    public Result getCountMap(OrderCountQueryVo orderCountQueryVo) {
        Map<String, Object> countMap = orderFeignClient.getCountMap(orderCountQueryVo);
        return Result.ok(countMap);
    }
}
