package com.qf.my.spring.cloud.rocketmq.producer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MySpringCloudRocketmqProducerApplicationTests {


    @Autowired
    private MyProducer producer;

    @Test
    void testSendMessage(){
        producer.sendMessage("hello spring cloud stream message");
    }

}
