package com.xdu.yygh.hosp.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdu.yygh.hosp.mapper.HospitalSetMapper;
import com.xdu.yygh.hosp.service.HospitalSetService;
import com.xdu.yygh.model.hosp.HospitalSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService{

//mapper会被mp自动注入
//    @Autowired
//    private HospitalSetMapper hospitalSetMapper;
}
