package com.xdu.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.yygh.model.hosp.HospitalSet;
import com.xdu.yygh.vo.order.SignInfoVo;

public interface HospitalSetService extends IService<HospitalSet>{
    String getSignKey(String hoscode);//mp自带的service

    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
