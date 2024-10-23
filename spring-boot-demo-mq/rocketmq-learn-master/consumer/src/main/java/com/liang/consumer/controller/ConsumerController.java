package com.liang.consumer.controller;

import com.liang.common.dto.TradeDTO;
import com.liang.consumer.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 生产控制器
 *
 * @author admin
 * @date 2021/09/13
 */
@RestController
@RequestMapping("/producer")
public class ConsumerController {


    @Resource
    AccountService service;


    @RequestMapping("/testConsumer")
    public void testConsumer(){
        service.save();
    }

    @PostMapping("/testTrade")
    public void testTrade(@RequestBody TradeDTO dto){
        service.trade(dto);
    }

}
