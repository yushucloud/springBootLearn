package com.qf.rocketmq.base;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author Thor
 * @公众号 Java架构栈
 */
public class BaseProducer {

    public static void main(String[] args) throws Exception {
        //1.创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("my-producer-group1");
        //2.指定nameserver的地址
        producer.setNamesrvAddr("172.16.253.101:9876");
        //3.启动生产者
        producer.start();
        //4.创建消息
        for (int i = 0; i < 10; i++) {
            Message message = new Message("MyTopic1","TagA",("hello rocketmq"+i).getBytes(StandardCharsets.UTF_8));
            //5.发送消息
            SendResult sendResult = producer.send(message);
            System.out.println(sendResult);
        }
        //5.关闭生产者
        producer.shutdown();

    }


}
