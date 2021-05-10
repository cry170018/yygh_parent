package com.xdu.yygh.msm.service;

import org.springframework.stereotype.Repository;

//@Repository
public interface MsmService {
    boolean send(String phone, String code);
}
