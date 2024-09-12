package com.oujiong.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@RequestMapping("/admin-api/mp/open/")
//@RequestMapping("/weixin")
public class WeiXInOauth {

//    http://yusu.v5.idcfengye.com/admin-api/mp/open/wxda51ce017a88681f
    /**
     * 设置云视讯的appid
     */
    @Value("${wxopen.appid}")
    private String appid;

    @Value("${wxopen.appsecret}")
    private String secret;
    @Value("${wxopen.redirect_url}")
    private String redirectUrl;

    @Value("${wxopen.qrCodeUrl}")
    private String qrCodeUrl;

    @Value("${wxopen.tokenUrl}")
    private String tokenUrl;

    @RequestMapping("/oauth")
    @ResponseBody
    public String test() {
        return getWeixinH5Code(appid, secret, redirectUrl);
    }


    /*尤其注意：跳转回调redirect_uri，应当使用https链接来确保授权code的安全性。

    参数说明

    参数	是否必须	说明
    appid	是	公众号的唯一标识
    redirect_uri	是	授权后重定向的回调链接地址， 请使用 urlEncode 对链接进行处理
    response_type	是	返回类型，请填写code
    scope	是	应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且， 即使在未关注的情况下，只要用户授权，也能获取其信息 ）
    state	否	重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
#wechat_redirect	是	无论直接打开还是做页面302重定向时候，必须带此参数
    forcePopup	否	强制此次授权需要用户弹窗确认；默认为false；需要注意的是，若用户命中了特殊场景下的静默授权逻辑，则此参数不生效
    用户操作后回调返回信息
    */
    /*通过code换取网页授权access_token
     * 用户点击下面生成的链接进行交互，同意或取消
     * */

    /*用户点击链接，用户同意后换取code*/
    public static String getWeixinH5Code(String appid, String appSecret, String redirectUrl) {
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

        return url
                .replace("APPID", appid)
                .replace("SECRET", appSecret)
                .replace("REDIRECT_URI", redirectUrl)
                .replace("SCOPE", "snsapi_userinfo").replace("STATE", getRandomString(20)).trim();
    }


    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/getQrCodeUrl")
    @ResponseBody
    public String getQrCodeUrl() throws UnsupportedEncodingException {
        String accessToken1 = getAccessToken();
//        https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
        String getQrCodeUrl = qrCodeUrl.replace("TOKEN", accessToken1);
        String sceneStr = getRandomString(20);
        String json = "{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\"" + ", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneStr + "\"}}}";
        String qrCodeResultString = HttpClient.post(getQrCodeUrl, json);

        JSONObject qrCodeResultJsonObject = (JSONObject) JSON.parse(qrCodeResultString);
        String url = qrCodeResultJsonObject.get("url").toString();
        String encodeUrl = URLEncoder.encode(url, "UTF-8");

        qrCodeResultJsonObject.put("url", encodeUrl);
        String jsonString = JSONObject.toJSONString(qrCodeResultJsonObject);

        String ticket = (String) qrCodeResultJsonObject.get("ticket");
        String  imgUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket;

        return imgUrl;
    }


    /**
     * 获取随机字符串
     */

    public static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取accessToken
     *
     * @return
     */
    public String getAccessToken() {
        String accessToken = null;
        //根据appid和appsecret获取access_token
        String getTokenUrl = tokenUrl.replace("APPID", appid).replace("APPSECRET", secret);
        String result = HttpClient.get(getTokenUrl);
        JSONObject jsonObject = JSONObject.parseObject(result);
        accessToken = jsonObject.getString("access_token");
        return accessToken;
    }
/*通过code换取网页授权access_token*/
    public Map<String, Object> getWxH5AccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        String newurl = url
                .replace("APPID", appid)
                .replace("SECRET", secret)
                .replace("CODE", code).trim();
        JSONObject jsonObject = JSONObject.parseObject(HttpClient.get(newurl));

        Map<String, Object> map = new HashMap<>();
        map.put("access_token", jsonObject.get("access_token"));
        map.put("expires_in", jsonObject.get("expires_in"));
        map.put("refresh_token", jsonObject.get("refresh_token"));
        map.put("openid", jsonObject.get("openid"));
        map.put("scope", jsonObject.get("scope"));
        return map;
    }

    public static JSONObject getH5UserInfo(String accesstoken, String openid) throws Exception {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        String newurl = url
                .replace("ACCESS_TOKEN", accesstoken)
                .replace("OPENID", openid).trim();
        return JSONObject.parseObject(HttpClient.get(newurl));


    }


//admin-api/mp/open/wxda51ce017a88681f
    /**
     * 如果用户同意授权，页面将跳转到
     */
//    @RequestMapping("/mp/open/wxda51ce017a88681f")
//    @ResponseBody
//    public JSONObject wxCallBack(HttpServletRequest request) throws Exception {
//        String code = request.getParameter("code");
//        String state = request.getParameter("state");
//        Map<String, Object> wxH5AccessToken = getWxH5AccessToken(code);
//        Object accessToken = wxH5AccessToken.get("access_token");
//        Object openid = wxH5AccessToken.get("openid");
//        JSONObject h5UserInfo = getH5UserInfo(accessToken.toString(), openid.toString());
//        return h5UserInfo;
//    }


    /**
     * 用户操作后，回调
     */
    @RequestMapping("/wxCallBack")
    @ResponseBody
    public JSONObject wxCallBack(HttpServletRequest request) throws Exception {
        /*用户同意后获取code*/
        String code = request.getParameter("code");

      /*
      * "access_token" -> "74_y2s00s-r1FDsaNzsAjawYPqm4ldWj1-1G45RxPmT_Zzp8ktUZnS6rPUEm7o9rFo0RtDToBfVJnDJgl0fZmk-cuk8-s0Y_TElNlj6GlVqoZ8"
"refresh_token" -> "74_WXJ9nkQHbW1bgf8DlL6H6w5QRRq84Z_zKN5EPTseDgR8jtC8Dqpk6x1jjfp3YL2Ln_9ukPN5WqDAjUpHy2TwLXiwKZY7qhJ1uOezQvxObNI"
"openid" -> "oVyxI6u16yc1RDrNpnrcop-XrI40"
"scope" -> "snsapi_userinfo"
"expires_in" -> {Integer@13174} 7200
*
      *
      * */
        Map<String, Object> wxH5AccessToken = getWxH5AccessToken(code);
        Object accessToken = wxH5AccessToken.get("access_token");
        Object openid = wxH5AccessToken.get("openid");
        JSONObject h5UserInfo = getH5UserInfo(accessToken.toString(), openid.toString());
        return h5UserInfo;
    }


    /**
     * 微信官方的回调处理
     * 官方文档:<a href="https://developers.weixin.qq.com/doc/offiaccount/Basic_Information/Access_Overview.html">...</a>
     *
     * @param request 微信发送的请求参数
     */
    @RequestMapping(value = "/wxda51ce017a88681f", produces = "application/xml; charset=UTF-8")
    @ResponseBody
    public JSONObject handleMessage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //获取微信请求参数
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echoStr = request.getParameter("echostr");
        String accessToken = getAccessToken();

        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
        String USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        USER_INFO_URL = USER_INFO_URL.replace("ACCESS_TOKEN", accessToken);
        USER_INFO_URL = USER_INFO_URL.replace("OPENID", inMessage.getFromUser());
        String info = HttpClient.get(USER_INFO_URL);
        JSONObject jsonObject = JSONObject.parseObject(info);

        System.out.println(info);
        return jsonObject;


       /* allFieldsMap = null
        toUser = "gh_a3c7101b6001"
        fromUser = "oVyxI6lyRO_IL_rS5LiL1eQnmbUo"
        createTime = {Long@11013} 1698935350
        event = "subscribe"
        eventKey = "qrscene_Iwx1svGxGp2ePeRHIWDS"
        ticket = "gQGz8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAybi1WVTVCbklkOEcxdUhJY2hCYzAAAgQrskNlAwSAOgkA"
        encrypt = "wfnvk1NThjWEfbY8Rmg0n/zVSg+bXp6L+H6e9H/2gxkabqQGchE91ndNnvDvd0GJeGhRXKby24XX7krKdrPlvplQwF+/Uq/R9hgRD5FMj/YQ4B4/jrvr1AjkPI3Xs+fMjsOgqVlHCkfCGFIyAHtVP245vnzsmA8P17NUhp+8CzDvir0BvMSM3jZxWRHc8mgIXOU5xU5e7u1BUrYp4fChfZxgClIUlda0EB+pipKzw5RtHzp0M1QjZWQwNGJdcD1SdMzPBOzb9qTm1ioIMoAmCJGkx35YPWsleCw/wA24gPY44lkD8qJFSjTzSzlXFgdn+z7jBdGY9NgFhrYMKx+BNtfzMfj07ttOqnHOybgMJcfdPlPQVyaQXyVH9W+uRI+P7L+LakKE89q5IjZ+pzmqKUznGw8sM1I7CTRba4hpejQqy6BzcMzsc/A2WJ6Vw0eh21NY98OCqaxizU1Id4Ef1DX9CeyhC36rojy9o9arn3lGigC9qcigqFNwc3abxRIWeOpniDgJTuvxsZEAJLX7eToOjk1aRYsCL2pJ3fYy1WJFaL+ZV9Vk4Sqzq/DldXpWe4C2/YTUnQYgM/otqBsZna5PlQ2LvtONvxYUvb9wnUlhJ4ssmpTFWmftiNDucCNV"*/
    }


}
