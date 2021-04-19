package com.xdu.yygh.hosp.controller;

import com.xdu.yygh.hosp.service.HospitalSetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {
    //注入service
    private HospitalSetService hospitalSetService;
}
