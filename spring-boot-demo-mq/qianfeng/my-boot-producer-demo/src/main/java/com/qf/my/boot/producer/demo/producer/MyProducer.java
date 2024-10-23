package com.qf.my.boot.producer.demo.producer;

import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @author Thor
 * @公众号 Java架构栈
 */
@Component
public class MyProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;


    public void sendMessage(String topic,String message){
        rocketMQTemplate.convertAndSend(topic,message);
    }

    /**
     * 发送事务消息
     * @param topic
     * @param msg
     * @throws InterruptedException
     */
    public void sendMessageInTransaction(String topic,String msg) throws InterruptedException {
        String[] tags = new String[]{"TagA","TagB","TagC","TagD","TagE"};
        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder.withPayload(msg).build();
            //topic和tag整合在一起
            String destination = topic+":"+tags[ i % tags.length];
            //第一个destination是消息要发送的目的地topic，第二个destination消息携带的业务数据
            TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(destination, message, destination);
            System.out.println(sendResult);
            Thread.sleep(10);
        }
    }

}
