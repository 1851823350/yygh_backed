package com.atwj.yygh.service;

import java.util.Map;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-16 9:14
 */
public interface WeiXinService {
    //生成支付二维码
    Map createNative(Long orderId);

    //查询微信的支付状态
    Map<String, String> queryPayStatus(Long orderId, String paymentType);

    //退款
    Boolean refund(Long orderId);

}
