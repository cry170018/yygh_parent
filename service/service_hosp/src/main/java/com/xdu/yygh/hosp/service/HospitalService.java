package com.xdu.yygh.hosp.service;

import com.xdu.yygh.model.hosp.Hospital;
import com.xdu.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface HospitalService {
    void save(Map<String, Object> paramMap);


    /**
     * 获取签名key
     * @param hoscode
     * @return
     */
    //String getSignKey(String hoscode);

    Hospital getByHoscode(String hoscode);
/*
* 条件分页查询
*
* */
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    void updateStatus(String id, Integer status);

    Map<String,Object> getHospById(String id);

    String getHospName(String hoscode);

    List<Hospital> findByHosname(String hosname);

    /**
     * 医院预约挂号详情
     */
    Map<String, Object> item(String hoscode);

}
