package com.atwj.yygh.service;

import com.atwj.yygh.model.order.OrderInfo;
import com.atwj.yygh.model.order.PaymentInfo;
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
public interface PaymentService extends IService<PaymentInfo> {

    //向支付表中添加信息
    void savePaymentInfo(OrderInfo order, Integer paymentType);

    //更新订单支付成功状态
    void paySuccess(String out_trade_no, Map<String, String> resultMap);

    //获取支付记录
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);
}
