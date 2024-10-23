package com.bart.listener;

import com.alibaba.fastjson.JSON;
import com.bart.model.Order;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Author noobBart
 * @Date 2023/7/09/0009 21:16
 */
@Component
@RocketMQMessageListener(topic = "bootOrderlyTopic",
consumerGroup = "boot-orderly-consumer-group",
consumeMode = ConsumeMode.ORDERLY, // 顺序消费模式，单线程
maxReconsumeTimes = 5 // 重试次数
)
public class BOrderlyMsgListener implements RocketMQListener<MessageExt> {
    @Override
    public void onMessage(MessageExt messageExt) {
        Order order = JSON.parseObject(new String(messageExt.getBody()), Order.class);
        System.out.println(order);
    }
}
