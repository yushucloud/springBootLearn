package com.liang.consumer.listener;

import com.liang.common.dto.TradeDTO;
import com.liang.consumer.domain.Account;
import com.liang.consumer.service.AccountService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Component
@RocketMQMessageListener(consumerGroup = "${rocketmq.consumer.group}",topic = "${rocketmq.consumer.topic}")
public class DefaultListener implements RocketMQListener<TradeDTO> {


    @Autowired
    AccountService service;

    @Transactional
    @Override
    public void onMessage(TradeDTO tradeDTO) {
        Account account = service.getAccount(tradeDTO.getIncrId());
        BigDecimal subtract = account.getBalance().add(tradeDTO.getAmount());
        account.setBalance(subtract);
        service.save(account);
    }


}
