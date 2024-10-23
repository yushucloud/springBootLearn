package com.liang.producer.controller;

import com.liang.common.dto.TradeDTO;
import com.liang.producer.service.AccountService;
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
public class ProducerController {


    @Resource
    AccountService service;


    @RequestMapping("/testProducer")
    public void testProducer(){
        service.save();
    }

    @PostMapping("/testTrade")
    public void testProducer(@RequestBody TradeDTO dto){
        service.trade(dto);
    }

}
