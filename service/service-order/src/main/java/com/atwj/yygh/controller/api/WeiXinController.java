package com.atwj.yygh.controller.api;

import com.atwj.yygh.commonResult.Result;
import com.atwj.yygh.enums.PaymentTypeEnum;
import com.atwj.yygh.service.PaymentService;
import com.atwj.yygh.service.WeiXinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 吴先森
 * @description: 预约前台微信支付接口
 * @create 2022-10-16 9:13
 */
@RestController
@RequestMapping("api/order/weixin")
public class WeiXinController {

    @Resource
    private WeiXinService weiXinService;
    @Resource
    private PaymentService paymentService;

    /*
     * 生成微信支付二维码
     */
    @GetMapping("/createNative/{orderId}")
    public Result createNative(@PathVariable Long orderId) {
        Map map = weiXinService.createNative(orderId);
        return Result.ok(map);
    }

    /*
     * 查询支付状态
     */
    @GetMapping("/queryPayStatus/{orderId}")
    public Result queryPayStatus(@PathVariable Long orderId){
        Map<String, String> resultMap = weiXinService.queryPayStatus(orderId, PaymentTypeEnum.WEIXIN.name());
        //判断支付状态
        if(resultMap == null){
            return Result.fail().message("支付出错");
        }
        if("SUCCESS".equals(resultMap.get("trade_state"))){
            //更新订单状态
            String out_trade_no = resultMap.get("out_trade_no");//订单编码
            paymentService.paySuccess(out_trade_no,resultMap);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中");
    }

}
