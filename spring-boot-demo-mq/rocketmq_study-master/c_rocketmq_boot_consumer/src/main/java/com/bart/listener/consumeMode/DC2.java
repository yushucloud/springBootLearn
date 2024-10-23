package com.bart.listener.consumeMode;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 广播模式
 * @Author noobBart
 * @Date 2023/7/11/0011 21:14
 */
@Component
@RocketMQMessageListener(topic = "modelTopic",
        consumerGroup = "mode-consumer-group-b",
        messageModel = MessageModel.BROADCASTING // 广播模式
)
public class DC2 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("我是 mode-consumer-group-b 组的第一个消费者：" + message);
    }
}
