package com.qf.my.boot.consumer.demo.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author Thor
 * @公众号 Java架构栈
 */
@Component
@RocketMQMessageListener(consumerGroup = "my-boot-consumer-group1",topic = "my-boot-topic")
public class MyConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("收到的消息:"+message);
    }
}
