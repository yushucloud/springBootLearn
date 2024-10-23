package com.qf.my.boot.consumer.demo;

import com.qf.my.boot.consumer.demo.producer.MyProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyBootProducerDemoApplicationTests {

    @Autowired
    private MyProducer producer;

    @Test
    void contextLoads() {
    }

    @Test
    void testSendMessage(){
        String topic = "my-boot-topic";
        String message = "hello rocket mq springboot message";
        producer.sendMessage(topic,message);
        System.out.println("消息发送成功！");
    }

    @Test
    void testSendMessageInTransaction() throws InterruptedException {
        String topic = "my-boot-topic";
        String message = "hello rocket mq transaction springboot message";
        producer.sendMessageInTransaction(topic,message);
        System.out.println("事务消息发送成功");
    }

}
