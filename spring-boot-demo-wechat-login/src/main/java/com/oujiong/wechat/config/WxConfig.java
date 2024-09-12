//package com.oujiong.wechat.config;
//
//import lombok.Data;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
///**
// * @author Hygge
// * @date 2022/08/13
// * @description 微信登录的一些配置信息 通过读取yml获得
// */
//@Component
//@Data
//@ConfigurationProperties(prefix = "wechat")
//public class WxConfig {
//    private String appId;
//    private String appSecret;
//    private String qrCodeUrl;
//    private String tokenUrl;
//    /**
//     * 验签使用的token
//     */
//    private String token;
//}