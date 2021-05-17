package com.xdu.yygh.msm.service;

import com.xdu.yygh.vo.msm.MsmVo;
import org.springframework.stereotype.Repository;

//@Repository
public interface MsmService {
    boolean send(String phone, String code);
    //mq使用，发送短信
    boolean send(MsmVo msmVo);
}
