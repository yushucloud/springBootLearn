package com.qf.my.spring.cloud.rocketmq.consumer;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

/**
 * @author Thor
 * @公众号 Java架构栈
 */
@Component
public class MyConsumer {

    @StreamListener(Sink.INPUT)
    public void processMessage(String message){
        System.out.println("收到的消息："+message);
    }
}
