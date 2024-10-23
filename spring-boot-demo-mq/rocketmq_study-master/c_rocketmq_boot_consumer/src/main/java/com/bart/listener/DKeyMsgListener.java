package com.bart.listener;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author noobBart
 * @Date 2023/7/11/0011 20:49
 */
@Component
@RocketMQMessageListener(topic = "bootKeyTopic",
        consumerGroup = "boot-key-consumer-group"
)
public class DKeyMsgListener implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        System.out.println(message.getBody());
    }
}
