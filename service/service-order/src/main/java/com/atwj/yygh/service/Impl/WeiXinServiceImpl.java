package com.atwj.yygh.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.atwj.yygh.enums.PaymentStatusEnum;
import com.atwj.yygh.enums.PaymentTypeEnum;
import com.atwj.yygh.enums.RefundStatusEnum;
import com.atwj.yygh.model.order.OrderInfo;
import com.atwj.yygh.model.order.PaymentInfo;
import com.atwj.yygh.model.order.RefundInfo;
import com.atwj.yygh.service.OrderService;
import com.atwj.yygh.service.PaymentService;
import com.atwj.yygh.service.RefundInfoService;
import com.atwj.yygh.service.WeiXinService;
import com.atwj.yygh.utils.ConstantPropertiesUtils;
import com.atwj.yygh.utils.HttpClient;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-16 9:14
 */
@Service
public class WeiXinServiceImpl implements WeiXinService {
    @Resource
    private OrderService orderService;
    @Resource
    private PaymentService paymentService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private RefundInfoService refundInfoService;

    //生成微信支付二维码
    @Override
    public Map createNative(Long orderId) {
        try {
            //从redis中获取信息
            Map payMap = (Map) redisTemplate.opsForValue().get(orderId.toString());
            if (payMap != null) {
                return payMap;
            }
            //1.根据orderId获取订单信息
            OrderInfo order = orderService.getById(orderId);

            //2.向支付表里面添加支付信息( PaymentTypeEnum.WEIXIN.getStatus()获取微信支付的状态码 )
            paymentService.savePaymentInfo(order, PaymentTypeEnum.WEIXIN.getStatus());

            //3.根据订单信息，拼装微信统一下单接口
            Map paramMap = new HashMap();
            // 公众账号ID
            paramMap.put("appid", ConstantPropertiesUtils.APPID);
            // 商户号
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr()); //获取随机字符串
            // 商品描述
            String body = order.getReserveDate() + "就诊" + order.getDepname();
            paramMap.put("body", body);
            paramMap.put("out_trade_no", order.getOutTradeNo());
            // 总金额，正确写法：paramMap.put("total_fee", order.getAmount().multiply(new BigDecimal("100")).longValue()+"");
            paramMap.put("total_fee", "1"); //为了测试，统一写成这个值
            // 终端IP
            paramMap.put("spbill_create_ip", "127.0.0.1");
            // 通知地址
            paramMap.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            // 交易类型
            paramMap.put("trade_type", "NATIVE");

            //4.调用微信统一下单接口,生成二维码,自定义httpclient类调用
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));//WXPayUtil.generateSignedXml(),将map转换为XML字符串，并进行加密（自动添加签名）
            client.setHttps(true);
            client.post();

            //5.返回相关数据
            String content = client.getContent();//返回xml文件
            //将xml文件转换成map集合
            Map<String, String> resultMap = WXPayUtil.xmlToMap(content);
            System.out.println(resultMap);

            //6 封装返回结果集
            Map map = new HashMap<>();
            map.put("orderId", orderId);
            map.put("totalFee", order.getAmount());
            map.put("resultCode", resultMap.get("result_code"));
            map.put("codeUrl", resultMap.get("code_url")); //二维码地址

            //利用redis，约定二维码在两个小时内有效
            if (resultMap.get("result_code") != null) {
                redisTemplate.opsForValue().set(orderId.toString(), map, 120, TimeUnit.MINUTES);
            }
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //查询微信的支付状态
    @Override
    public Map<String, String> queryPayStatus(Long orderId, String paymentType) {
        try {
            OrderInfo orderInfo = orderService.getById(orderId);
            //1、封装参数
            Map paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);
            paramMap.put("out_trade_no", orderInfo.getOutTradeNo());
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            //2、设置请求
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY));
            client.setHttps(true);
            client.post();
            //3、返回第三方的数据，转成Map
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            //4、返回
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    //退款
    @Override
    public Boolean refund(Long orderId) {

        try {
            //获取已支付的付款信息
            PaymentInfo paymentInfo = paymentService.getPaymentInfo(orderId, PaymentTypeEnum.WEIXIN.getStatus());
            //添加退款信息到退款表中
            RefundInfo refundInfo = refundInfoService.saveRefundInfo(paymentInfo);
            //判断当前订单是否退款
            if (refundInfo.getRefundStatus().intValue() == RefundStatusEnum.REFUND.getStatus().intValue()) {
                return true;
            }
            //调用微信接口实现退款
            //封装需要参数
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("appid", ConstantPropertiesUtils.APPID);       //公众账号ID
            paramMap.put("mch_id", ConstantPropertiesUtils.PARTNER);   //商户编号
            paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
            paramMap.put("transaction_id", paymentInfo.getTradeNo()); //微信订单号
            paramMap.put("out_trade_no", paymentInfo.getOutTradeNo()); //商户订单编号
            paramMap.put("out_refund_no", "tk" + paymentInfo.getOutTradeNo()); //商户退款单号

            paramMap.put("total_fee", "1");
            paramMap.put("refund_fee", "1");
            String paramXml = null;
            paramXml = WXPayUtil.generateSignedXml(paramMap, ConstantPropertiesUtils.PARTNERKEY);
            //设置调用接口内容
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/secapi/pay/refund");
            client.setXmlParam(paramXml);
            client.setHttps(true);
            //设置证书信息
            client.setCert(true);
            client.setCertPassword(ConstantPropertiesUtils.PARTNER);
            client.post();

            //接收返回数据
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);
            if (null != resultMap && WXPayConstants.SUCCESS.equalsIgnoreCase(resultMap.get("result_code"))) {
                refundInfo.setCallbackTime(new Date());
                refundInfo.setTradeNo(resultMap.get("refund_id"));
                refundInfo.setRefundStatus(RefundStatusEnum.REFUND.getStatus());
                refundInfo.setCallbackContent(JSONObject.toJSONString(resultMap));
                refundInfoService.updateById(refundInfo);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
