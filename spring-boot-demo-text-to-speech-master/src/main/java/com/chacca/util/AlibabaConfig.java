package com.chacca.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class AlibabaConfig {
    //这里读取文件一直出问题 所以就写死了
    static String path = "/Users/zhaowenxuan/记录文档/文字/TextToSpeech/src/main/resources/alibaba.properties";
    static InputStream stream = null;
    static ResourceBundle bundle = null;
    static {
        try {
            stream = new FileInputStream(path);
            bundle = new PropertyResourceBundle(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String getAppkey(){
        String appKey = bundle.getString("appKey");
        return appKey;
    }
    public static String getAccessKeyId(){
        String accessKeyId = bundle.getString("accessKeyId");
        return accessKeyId;
    }
    public static String getAccessKeySecret(){
        String accessKeySecret = bundle.getString("accessKeySecret");
        return accessKeySecret;
    }
}
