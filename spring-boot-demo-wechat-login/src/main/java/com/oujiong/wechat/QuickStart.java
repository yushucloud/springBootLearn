//package com.oujiong.wechat;
//
//import com.wechat.pay.java.core.Config;
//import com.wechat.pay.java.core.RSAAutoCertificateConfig;
//import com.wechat.pay.java.service.payments.nativepay.NativePayService;
//import com.wechat.pay.java.service.payments.nativepay.model.Amount;
//import com.wechat.pay.java.service.payments.nativepay.model.PrepayRequest;
//import com.wechat.pay.java.service.payments.nativepay.model.PrepayResponse;
//
///** Native 支付下单为例 */
//public class QuickStart {
//
//    /** 商户号 */
//    public static String merchantId = "1600969217";
//    /** 商户API私钥路径 */
//    public static String privateKeyPath = "D:\\wiexin\\WXCertUtil\\cert\\1600969217_20231101_cert\\apiclient_cert.p12";
//    /** 商户证书序列号 */
//    public static String merchantSerialNumber = "6978834EB1743D30A6621AA7CB3727E8624712E3";
//    /** 商户APIV3密钥 */
//    public static String apiV3Key = "M39pTBLwGju5nQHfYMcGCjLgHtPvET2f";
//
//    public static void main(String[] args) {
//        // 使用自动更新平台证书的RSA配置
//        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
//        Config config =
//                new RSAAutoCertificateConfig.Builder()
//                        .merchantId(merchantId)
//                        .privateKeyFromPath(privateKeyPath)
//                        .merchantSerialNumber(merchantSerialNumber)
//                        .apiV3Key(apiV3Key)
//                        .build();
//        // 构建service
//        NativePayService service = new NativePayService.Builder().config(config).build();
//        // request.setXxx(val)设置所需参数，具体参数可见Request定义
//        PrepayRequest request = new PrepayRequest();
//        Amount amount = new Amount();
//        amount.setTotal(100);
//        request.setAmount(amount);
//        request.setAppid("wxa9d9651ae******");
//        request.setMchid("190000****");
//        request.setDescription("测试商品标题");
//        request.setNotifyUrl("https://notify_url");
//        request.setOutTradeNo("out_trade_no_001");
//        // 调用下单方法，得到应答
//        PrepayResponse response = service.prepay(request);
//        // 使用微信扫描 code_url 对应的二维码，即可体验Native支付
//        System.out.println(response.getCodeUrl());
//    }
//}