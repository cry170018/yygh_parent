package com.xdu.yygh.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.yygh.model.order.PaymentInfo;

import java.util.Map;

public interface WeixinService  {
    /**
     * 根据订单号下单，生成支付链接
     */
    Map createNative(Long orderId);

    Map<String,String> queryPayStatus(Long orderId, String name);


}
