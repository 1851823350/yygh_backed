package com.atwj.yygh.service;

import com.atwj.yygh.model.order.OrderInfo;
import com.atwj.yygh.vo.order.OrderCountQueryVo;
import com.atwj.yygh.vo.order.OrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-15 12:27
 */
public interface OrderService extends IService<OrderInfo> {
    //保存订单
    Long saveOrder(String scheduleId, Long patientId);

    //根据orderId查询订单
    OrderInfo getOrder(String orderId);

    //分页查询订单列表
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);

    //后台管理系统查看所有订单
    Map<String,Object> show(Long id);

    //取消预约
    Boolean cancelOrder(Long orderId);

    //就诊提醒
    void patientTips();

    //统计订单数据
    Map<String, Object> getCountMap(OrderCountQueryVo orderCountQueryVo);

}
