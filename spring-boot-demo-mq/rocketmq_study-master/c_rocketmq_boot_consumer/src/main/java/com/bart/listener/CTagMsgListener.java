package com.bart.listener;

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author noobBart
 * @Date 2023/7/10/0010 22:43
 */
@Component
@RocketMQMessageListener(topic = "bootOrderlyTapTopic",
        consumerGroup = "boot-tag-consumer-group",
        selectorType = SelectorType.TAG,
        selectorExpression = "TagA"
)
public class CTagMsgListener implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt message) {
        System.out.println(message.toString());
    }
}
