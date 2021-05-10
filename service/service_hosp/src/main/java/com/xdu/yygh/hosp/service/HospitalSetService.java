package com.xdu.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.yygh.model.hosp.HospitalSet;

public interface HospitalSetService extends IService<HospitalSet>{
    String getSignKey(String hoscode);//mp自带的service
}
