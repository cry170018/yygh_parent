package com.xdu.yygh.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.yygh.model.order.OrderInfo;
import com.xdu.yygh.model.order.PaymentInfo;

import java.util.Map;

public interface PaymentService extends IService<PaymentInfo> {
    void savePaymentInfo(OrderInfo order, Integer status);

    void paySuccess(String out_trade_no, Integer status, Map<String, String> resultMap);
}
