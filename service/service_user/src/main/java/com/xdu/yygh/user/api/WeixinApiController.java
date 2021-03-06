package com.xdu.yygh.user.api;

import com.alibaba.fastjson.JSONObject;
import com.xdu.yygh.common.exception.YyghException;
import com.xdu.yygh.common.helper.JwtHelper;
import com.xdu.yygh.common.result.Result;
import com.xdu.yygh.common.result.ResultCodeEnum;
import com.xdu.yygh.model.user.UserInfo;
import com.xdu.yygh.user.service.UserInfoService;
import com.xdu.yygh.user.utils.ConstantWxPropertiesUtils;
//import com.xdu.yygh.user.utils.HttpClientUtils;
import com.sun.deploy.net.URLEncoder;
import com.xdu.yygh.user.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/api/ucenter/wx")

public class WeixinApiController {

    @Autowired
    private UserInfoService userInfoService;

    //1.生成微信扫描二维码
    @GetMapping("getLoginParam")
    @ResponseBody
    public Result genQrConnect(HttpSession session) {
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("appid", ConstantWxPropertiesUtils.WX_OPEN_APP_ID);
            map.put("scope","snsapi_login");
            String wxOpenRedirectUrl = ConstantWxPropertiesUtils.WX_OPEN_REDIRECT_URL;
            wxOpenRedirectUrl = URLEncoder.encode(wxOpenRedirectUrl, "utf-8");
          //  map.put("redirect_uri","snsapi_login");
            map.put("redirect_uri",wxOpenRedirectUrl);
            map.put("state",System.currentTimeMillis()+"");
            return Result.ok(map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    //微信扫描后回调的方法
    @GetMapping("callback")
    public String callback(String code,String state) {

        //第一步 获取临时票据 code
        System.out.println("code:"+code);
        //第二步 拿着code和微信id和秘钥，请求微信固定地址 ，得到两个值
        //使用code和appid以及appscrect换取access_token
        //  %s   占位符   类似sql中的？
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");
        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantWxPropertiesUtils.WX_OPEN_APP_ID,
                ConstantWxPropertiesUtils.WX_OPEN_APP_SECRET,
                code);
        //使用httpclient请求这个地址
        try {
            String accesstokenInfo = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accesstokenInfo:"+accesstokenInfo);
            //从返回字符串获取两个值 openid  和  access_token
            JSONObject jsonObject = JSONObject.parseObject(accesstokenInfo);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");

            //判断数据库是否存在微信的扫描人信息
            //根据openid判断
            UserInfo userInfo = userInfoService.selectWxInfoOpenId(openid);
            if(userInfo == null) { //数据库不存在微信信息
                //第三步 拿着openid  和  access_token请求微信地址，得到扫描人信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String resultInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println("resultInfo:"+resultInfo);
                JSONObject resultUserInfoJson = JSONObject.parseObject(resultInfo);
                //解析用户信息
                //用户昵称
                String nickname = resultUserInfoJson.getString("nickname");
                //用户头像
                String headimgurl = resultUserInfoJson.getString("headimgurl");

                //获取扫码人信息添加数据库
                userInfo = new UserInfo();
                userInfo.setNickName(nickname);
                userInfo.setOpenid(openid);
                userInfo.setStatus(1);
                userInfoService.save(userInfo);
            }
            //返回name和token字符串
            Map<String,String> map = new HashMap<>();
            String name = userInfo.getName();
            if(StringUtils.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if(StringUtils.isEmpty(name)) {
                name = userInfo.getPhone();
            }
            map.put("name", name);

            //判断userInfo是否有手机号，如果手机号为空，返回openid
            //如果手机号不为空，返回openid值是空字符串
            //前端判断：如果openid不为空，绑定手机号，如果openid为空，不需要绑定手机号
            if(StringUtils.isEmpty(userInfo.getPhone())) {
                map.put("openid", userInfo.getOpenid());
            } else {
                map.put("openid", "");
            }
            //使用jwt生成token字符串
            String token = JwtHelper.createToken(userInfo.getId(), name);
            map.put("token", token);
            //跳转到前端页面，在弹出的小框里
            return "redirect:" + ConstantWxPropertiesUtils.YYGH_BASE_URL + "/weixin/callback?token="+map.get("token")+ "&openid="+map.get("openid")+"&name="+URLEncoder.encode(map.get("name"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//    //2.回调的方法，得到扫描人信息
//    /**
//     * 微信登录回调
//     *
//     * @param code
//     * @param state
//     * @return
//     */
//    @RequestMapping("callback")
//    public String callback(String code, String state) {
//        //获取授权临时票据
//        System.out.println("微信授权服务器回调。。。。。。");
//        System.out.println("state = " + state);
//        System.out.println("code = " + code);
//
//        if (StringUtils.isEmpty(state) || StringUtils.isEmpty(code)) {
////            log.error("非法回调请求");
//            throw new YyghException(ResultCodeEnum.ILLEGAL_CALLBACK_REQUEST_ERROR);
//        }
//
//        //使用code和appid以及appscrect换取access_token
//        StringBuffer baseAccessTokenUrl = new StringBuffer()
//                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
//                .append("?appid=%s")
//                .append("&secret=%s")
//                .append("&code=%s")
//                .append("&grant_type=authorization_code");
//
//        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
//                ConstantWxPropertiesUtils.WX_OPEN_APP_ID,
//                ConstantWxPropertiesUtils.WX_OPEN_APP_SECRET,
//                code);
//
//        String result = null;
//        try {
//            result = HttpClientUtils.get(accessTokenUrl);
//        } catch (Exception e) {
//            throw new YyghException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
//        }
//
//        System.out.println("使用code换取的access_token结果 = " + result);
//
//        JSONObject resultJson = JSONObject.parseObject(result);
//        if(resultJson.getString("errcode") != null){
//            //log.error("获取access_token失败：" + resultJson.getString("errcode") + resultJson.getString("errmsg"));
//            throw new YyghException(ResultCodeEnum.FETCH_ACCESSTOKEN_FAILD);
//        }
//
//        String accessToken = resultJson.getString("access_token");
//        String openId = resultJson.getString("openid");
////        log.info(accessToken);
////        log.info(openId);
//
//        //根据access_token获取微信用户的基本信息
//        //先根据openid进行数据库查询
//        // UserInfo userInfo = userInfoService.getByOpenid(openId);
//        // 如果没有查到用户信息,那么调用微信个人信息获取的接口
//        // if(null == userInfo){
//        //如果查询到个人信息，那么直接进行登录
//        //使用access_token换取受保护的资源：微信的个人信息
//        String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
//                "?access_token=%s" +
//                "&openid=%s";
//        String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openId);
//        String resultUserInfo = null;
//        try {
//            resultUserInfo = HttpClientUtils.get(userInfoUrl);
//        } catch (Exception e) {
//            throw new YyghException(ResultCodeEnum.FETCH_USERINFO_ERROR);
//        }
//        System.out.println("使用access_token获取用户信息的结果 = " + resultUserInfo);
//
//        JSONObject resultUserInfoJson = JSONObject.parseObject(resultUserInfo);
//        if(resultUserInfoJson.getString("errcode") != null){
////            log.error("获取用户信息失败：" + resultUserInfoJson.getString("errcode") + resultUserInfoJson.getString("errmsg"));
//            throw new YyghException(ResultCodeEnum.FETCH_USERINFO_ERROR);
//        }
//
//        //解析用户信息
//        String nickname = resultUserInfoJson.getString("nickname");
//        String headimgurl = resultUserInfoJson.getString("headimgurl");
//
//        UserInfo userInfo = new UserInfo();
//        userInfo.setOpenid(openId);
//        userInfo.setNickName(nickname);
//        userInfo.setStatus(1);
//        userInfoService.save(userInfo);
//        // }
//
//        Map<String, Object> map = new HashMap<>();
//        String name = userInfo.getName();
//        if(StringUtils.isEmpty(name)) {
//            name = userInfo.getNickName();
//        }
//        if(StringUtils.isEmpty(name)) {
//            name = userInfo.getPhone();
//        }
//        map.put("name", name);
//        if(StringUtils.isEmpty(userInfo.getPhone())) {
//            map.put("openid", userInfo.getOpenid());
//        } else {
//            map.put("openid", "");
//        }
//        String token = JwtHelper.createToken(userInfo.getId(), name);
//        map.put("token", token);
//        return "redirect:" + ConstantWxPropertiesUtils.YYGH_BASE_URL + "/weixin/callback?token="+map.get("token")+"&openid="+map.get("openid")+"&name="+URLEncoder.encode((String)map.get("name"));
//    }


}
