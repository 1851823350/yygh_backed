package com.atwj.yygh.service;

import com.atwj.yygh.model.order.PaymentInfo;
import com.atwj.yygh.model.order.RefundInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-16 12:35
 */
public interface RefundInfoService extends IService<RefundInfo> {
    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);
}
