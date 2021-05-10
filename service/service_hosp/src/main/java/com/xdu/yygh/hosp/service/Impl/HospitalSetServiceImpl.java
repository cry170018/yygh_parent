package com.xdu.yygh.hosp.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xdu.yygh.common.exception.YyghException;
import com.xdu.yygh.common.result.ResultCodeEnum;
import com.xdu.yygh.hosp.mapper.HospitalSetMapper;
import com.xdu.yygh.hosp.service.HospitalSetService;
import com.xdu.yygh.model.hosp.HospitalSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService{
    @Autowired
    private HospitalSetMapper hospitalSetMapper;
    //2 根据传递过来医院编码，查询数据库，查询签名

    @Override
    public String getSignKey(String hoscode) {
        HospitalSet hospitalSet = this.getByHoscode(hoscode);
        if(null == hospitalSet) {
            throw new YyghException(ResultCodeEnum.HOSPITAL_OPEN);
        }
        if(hospitalSet.getStatus().intValue() == 0) {
            throw new YyghException(ResultCodeEnum.HOSPITAL_LOCK);
        }
        return hospitalSet.getSignKey();
    }

    /**
     * 根据hoscode获取医院设置
     * @param hoscode
     * @return
     */
    private HospitalSet getByHoscode(String hoscode) {
        return hospitalSetMapper.selectOne(new QueryWrapper<HospitalSet>().eq("hoscode", hoscode));
    }


//mapper会被mp自动注入
//    @Autowired
//    private HospitalSetMapper hospitalSetMapper;
}
