package com.liang.producer.service;

import com.liang.common.dto.TradeDTO;
import com.liang.producer.domain.Account;
import com.liang.producer.respository.AccountRepository;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class AccountService {


    @Resource
    AccountRepository repository;

    @Resource
    RocketMQTemplate rocketMQTemplate;

    private static final String TOPIC_NAME = "spring-boot-topic";


    public void save(){
        Account account = new Account();
        account.setName("liang");
        account.setBalance(new BigDecimal(1000));
        account.setTime(new Date());
        repository.save(account);
    }

    public void save(Account account){
        repository.save(account);
    }

    public Account getAccount(String id) {
        return repository.getById(id);
    }

    @Transactional
    public void trade(TradeDTO tradeDTO){
        String txId = UUID.randomUUID().toString();
        Account account = this.getAccount(tradeDTO.getReduceId());
        BigDecimal subtract = account.getBalance().subtract(tradeDTO.getAmount());
        account.setBalance(subtract);
        this.save(account);
        MessageBuilder<TradeDTO> builder = MessageBuilder.withPayload(tradeDTO);
        builder.setHeader(RocketMQHeaders.KEYS, txId);
//        int i = 1/0;
        rocketMQTemplate.sendMessageInTransaction(TOPIC_NAME,builder.build(),txId);
    }

}
