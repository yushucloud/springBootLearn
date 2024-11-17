package com.chacca.controller;

import com.chacca.service.VoiceService;
import com.chacca.util.AlibabaConfig;
import com.chacca.util.SpeechSynthesizerDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class pageController {
    @Autowired
    private VoiceService service;
    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/make")
    @ResponseBody
    public String makeFile(@RequestParam("text") String text
            ,@RequestParam(required = false,defaultValue = "siyue",value = "voice")String Voice
            ,@RequestParam(required = false,defaultValue = "100")int PitchRate
            ,@RequestParam(required = false,defaultValue = "100")int Speech){
        String appkey = AlibabaConfig.getAppkey();
        String accessKeyId = AlibabaConfig.getAccessKeyId();
        String accessKeySecret = AlibabaConfig.getAccessKeySecret();
        System.out.println(text+" "+Voice+" "+PitchRate+" "+Speech);
        try {
            SpeechSynthesizerDemo demo = new SpeechSynthesizerDemo(appkey,accessKeyId,accessKeySecret);
            demo.process(text,Voice,PitchRate,Speech);
            demo.shutdown();
        } catch (Exception e) {
            return "error";
        }finally {
            System.out.println("返回结果  ->"+SpeechSynthesizerDemo.resultInfo+","+SpeechSynthesizerDemo.resultPath);
        }
        return "success";
    }
    @GetMapping("/config")
    public String config(){
        return "Table";
    }
    @GetMapping("QueryCfg")
    @ResponseBody
    public String QueryCfg(@RequestParam("page")int page,@RequestParam("limit")int limit){
        int count = service.QueryCount();
        System.out.println("查全部个数 ->"+count);
        return "123";
    }
}
