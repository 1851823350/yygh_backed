package com.atwj.yygh.controller;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.service.OrderService;
import com.atwj.yygh.vo.order.OrderCountQueryVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 吴先森
 * @description: 后台订单管理接口
 * @create 2022-10-15 18:57
 */
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderController {
    @Resource
    private OrderService orderService;

    @ApiOperation(value = "获取订单")
    @GetMapping("show/{id}")
    public Result get(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @PathVariable Long id) {
        return Result.ok(orderService.show(id));
    }

    @ApiOperation(value = "获取订单统计数据")
    @PostMapping("inner/getCountMap")
    public Map<String, Object> getCountMap(@RequestBody OrderCountQueryVo orderCountQueryVo) {
        return orderService.getCountMap(orderCountQueryVo);
    }
}
