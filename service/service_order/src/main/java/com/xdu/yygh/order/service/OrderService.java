package com.xdu.yygh.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.yygh.model.order.OrderInfo;
import com.xdu.yygh.vo.order.OrderQueryVo;

import java.util.Map;

public interface OrderService extends IService<OrderInfo> {
    //保存订单
    Long saveOrder(String scheduleId, Long patientId);

    OrderInfo getOrder(String orderId);

    /**
     * 分页列表
     */
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);
    /**
     * 订单详情
     * @param orderId
     * @return
     */
    Map<String,Object> show(Long orderId);

}

