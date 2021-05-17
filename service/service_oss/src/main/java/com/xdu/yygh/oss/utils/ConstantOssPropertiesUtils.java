package com.xdu.yygh.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConstantOssPropertiesUtils implements InitializingBean {
//    aliyun.oss.endpoint=https://oss-cn-beijing.aliyuncs.com
//    aliyun.oss.accessKeyId=LTAI5tLoUhpeva7YTswP9XXc
//    aliyun.oss.secret=kRsSSzAWmoW9tXmkbQEeQV1pMX5kqd

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.secret}")
    private String secret;

    @Value("${aliyun.oss.bucket}")
    private String bucket;

    public static String ENDPOINT;
    public static String ACCESS_KEY_ID;
    public static String SECRECT;
    public static String BUCKET;


    @Override
    public void afterPropertiesSet() throws Exception {
        ENDPOINT=endpoint;
        ACCESS_KEY_ID=accessKeyId;
        SECRECT=secret;
        BUCKET=bucket;
    }
}

