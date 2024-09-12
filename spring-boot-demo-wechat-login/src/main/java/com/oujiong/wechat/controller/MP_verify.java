package com.oujiong.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class MP_verify {
    @RequestMapping("/MP_verify_RyTgATT8cZcPLWq8.txt")
    @ResponseBody
    public String test() {
        return "RyTgATT8cZcPLWq8";
    }

}
