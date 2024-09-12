package com.oujiong.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.security.MessageDigest;
public class DemoController {


    //...
    @RequestMapping(value="/checkToken",method= RequestMethod.GET)
    public String  checkToken(HttpServletRequest req, HttpServletResponse resp)throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String message = "success";
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");
        try {
            String[] arr = {"sjyx", timestamp, nonce};
            Arrays.sort(arr);

            StringBuilder content = new StringBuilder();
            for (int i = 0; i < arr.length; i++) {
                content.append(arr[i]);
            }
            //sha1Hex 加密
            MessageDigest md = null;
            String temp = null;
            md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(content.toString().getBytes());
            temp = byteToStr(digest);
            if ((temp.toLowerCase()).equals(signature)){
                return echostr;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
    private static String byteToStr(byte[] byteArray){
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
    private static String byteToHexStr(byte mByte){
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A','B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4)& 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    @RequestMapping(value="/checkToken",method=RequestMethod.POST)
    public String  responseEvent(HttpServletRequest req, HttpServletResponse resp) {
        String message = "success";
        try {
            //把微信返回的xml信息转义成map
            Map<String, String> map = XmlUtil.xmlToMap(req);
            System.out.println("微信接收到的消息为:"+map.toString());
            String openId = map.get("FromUserName");//消息来源用户标识
            String toUserName = map.get("ToUserName");//消息目的用户标识
            String msgType = map.get("MsgType");//消息类型(event或者text)
            String EventKey = map.get("EventKey");//消息来源用户标识
            if(EventKey.contains("_")){ //scene_id首次关注会有前缀，去掉就和生成二维码时带的一致了
                EventKey = EventKey.split("_")[1];
            }
            String scene_Id = EventKey;
            String eventType = map.get("Event");//事件类型
            if("subscribe".equals(eventType) || "SCAN".equals(eventType) ){ //关注  or 浏览
                //获取缓存的最新的公众号 token
//                PcAccessToken token = accessTokenService.findPcToken("");
//                String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
//                USER_INFO_URL = USER_INFO_URL.replace("ACCESS_TOKEN", token.getAccessToken());
//                USER_INFO_URL = USER_INFO_URL.replace("OPENID", openId);
//                String info = HttpUtil.getRequest(USER_INFO_URL);
//                System.out.println("----user-----"+info);
//                JSONObject obj = JSONObject.fromObject(info);
//                String unionid = obj.getString("unionid");
//                String nickname = obj.getString("nickname");
//                String headimgurl = obj.getString("headimgurl");
//                String city = obj.getString("city");
//                String province = obj.getString("province");
//                String qr_scene = obj.getString("qr_scene");
                //获取到用户信息后处理登录逻辑，比如保存用户信息，用户表scene_id 字段，保存二维码的scend_id，供前端查询用户登录情况。
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }


}
