package com.xdu.yygh.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xdu.yygh.model.user.UserInfo;
import com.xdu.yygh.vo.user.LoginVo;
import com.xdu.yygh.vo.user.UserAuthVo;
import com.xdu.yygh.vo.user.UserInfoQueryVo;

import java.util.Map;

public interface UserInfoService extends IService<UserInfo> {
    Map<String,Object> loginUser(LoginVo loginVo);

    UserInfo selectWxInfoOpenId(String openid);

    void userAuth(Long userId, UserAuthVo userAuthVo);

    UserInfo getByOpenid(String openid);

    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);

    void lock(Long userId, Integer status);

    Map<String,Object> show(Long userId);

    //认证审批  2通过  -1不通过
    void approval(Long userId, Integer authStatus);
}

