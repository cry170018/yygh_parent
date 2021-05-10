package com.xdu.yygh.user.controller;

import com.xdu.yygh.common.result.Result;
import com.xdu.yygh.user.service.UserInfoService;
import com.xdu.yygh.vo.user.LoginVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation(value = "会员登录")
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo, HttpServletRequest request) {
     //   loginVo.setIp(IpUtil.getIpAddr(request));
        Map<String, Object> info = userInfoService.loginUser(loginVo);
        return Result.ok(info);
    }

}

