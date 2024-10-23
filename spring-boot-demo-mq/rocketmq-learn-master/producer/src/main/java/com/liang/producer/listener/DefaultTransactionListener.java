package com.liang.producer.listener;


import com.liang.common.dto.TradeDTO;
import com.liang.producer.domain.Account;
import com.liang.producer.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
@RocketMQTransactionListener
public class DefaultTransactionListener implements RocketMQLocalTransactionListener {

    @Resource
    AccountService accountService;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
//        try {
//            TradeDTO tradeDTO = (TradeDTO)msg.getPayload();
//            Account account = accountService.getAccount(tradeDTO.getReduceId());
//            BigDecimal subtract = account.getBalance().subtract(tradeDTO.getAmount());
//            account.setBalance(subtract);
//            accountService.save(account);
//            return RocketMQLocalTransactionState.COMMIT;
//        } catch (Exception e) {
//            log.error("executeLocalTransaction ex",e);
//            return RocketMQLocalTransactionState.ROLLBACK;
//        }
        return RocketMQLocalTransactionState.COMMIT;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {

        return RocketMQLocalTransactionState.COMMIT;
    }
}
