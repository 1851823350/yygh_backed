package com.atwj.yygh.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.atwj.yygh.HospitalFeignClient;
import com.atwj.yygh.common.helper.HttpRequestHelper;
import com.atwj.yygh.enums.OrderStatusEnum;
import com.atwj.yygh.enums.PaymentStatusEnum;
import com.atwj.yygh.enums.PaymentTypeEnum;
import com.atwj.yygh.mapper.PaymentMapper;
import com.atwj.yygh.model.order.OrderInfo;
import com.atwj.yygh.model.order.PaymentInfo;
import com.atwj.yygh.service.OrderService;
import com.atwj.yygh.service.PaymentService;
import com.atwj.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-15 12:28
 */
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, PaymentInfo> implements PaymentService {
    @Resource
    private OrderService orderService;
    @Resource
    private HospitalFeignClient hospitalFeignClient;

    @Override
    public void savePaymentInfo(OrderInfo order, Integer paymentType) {
        //1.根据orderId和status查询数据库中是否存在相同订单
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", order.getId());
        wrapper.eq("payment_type", paymentType);
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            return;
        }

        //设置支付信息
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setCreateTime(new Date());
        paymentInfo.setOrderId(order.getId());
        paymentInfo.setPaymentType(paymentType);
        paymentInfo.setOutTradeNo(order.getOutTradeNo());
        paymentInfo.setPaymentStatus(PaymentStatusEnum.UNPAID.getStatus());
        String subject = new DateTime(order.getReserveDate()).toString("yyyy-MM-dd")+"|"+order.getHosname()+"|"+order.getDepname()+"|"+order.getTitle();//支付信息的完整内容
        paymentInfo.setSubject(subject);
        paymentInfo.setTotalAmount(order.getAmount());
        //添加支付信息
        baseMapper.insert(paymentInfo);
    }

    @Override
    public void paySuccess(String out_trade_no, Map<String, String> resultMap) {
        //1. 根据订单编号查询支付记录
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("out_trade_no", out_trade_no);
        wrapper.eq("payment_type", PaymentTypeEnum.WEIXIN.getStatus());
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);

        //2. 更新支付信息
        paymentInfo.setPaymentStatus(PaymentStatusEnum.PAID.getStatus());
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setTradeNo(resultMap.get("transaction_id"));
        paymentInfo.setCallbackContent(resultMap.toString());
        baseMapper.updateById(paymentInfo);

        /**
         * 3. 根据订单编号得到订单信息
         * 4.更新订单信息
         */
        OrderInfo orderInfo = orderService.getById(paymentInfo.getOrderId());
        orderInfo.setOrderStatus(OrderStatusEnum.PAID.getStatus());
        orderService.updateById(orderInfo);

        //5.调用医院接口，更新订单支付信息
        SignInfoVo signInfoVo = hospitalFeignClient.getSignInfoVo(orderInfo.getHoscode());
        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("hoscode",orderInfo.getHoscode());
        reqMap.put("hosRecordId",orderInfo.getHosRecordId());
        reqMap.put("timestamp", HttpRequestHelper.getTimestamp());
        String sign = HttpRequestHelper.getSign(reqMap, signInfoVo.getSignKey());
        reqMap.put("sign", sign);
        JSONObject result = HttpRequestHelper.sendRequest(reqMap, signInfoVo.getApiUrl() + "/order/updatePayStatus");
    }

    //获取支付记录
    @Override
    public PaymentInfo getPaymentInfo(Long orderId, Integer paymentType) {
        QueryWrapper<PaymentInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("order_id", orderId);
        wrapper.eq("payment_type", paymentType);
        PaymentInfo paymentInfo = baseMapper.selectOne(wrapper);
        return paymentInfo;
    }
}
