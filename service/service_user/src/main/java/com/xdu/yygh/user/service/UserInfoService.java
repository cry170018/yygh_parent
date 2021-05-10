package com.xdu.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.yygh.model.user.UserInfo;
import com.xdu.yygh.vo.user.LoginVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {
    Map<String,Object> loginUser(LoginVo loginVo);

    UserInfo selectWxInfoOpenId(String openid);
}

