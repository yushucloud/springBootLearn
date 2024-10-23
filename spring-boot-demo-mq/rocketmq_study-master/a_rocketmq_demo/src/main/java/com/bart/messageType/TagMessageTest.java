package com.bart.messageType;

import com.bart.common.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

/**
 * 带标签的消息
 *
 * @Author noobBart
 * @Date 2023/7/04/0004 20:31
 */
public class TagMessageTest {
    @Test
    public void testTagProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("tag-producer-group");
        // 设置nameServer地址
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        // 启动实例
        producer.start();
        List<Message> msgs = Arrays.asList(
                new Message("TagTopic", "vip1", "我是一个vip1的消息".getBytes()),
                new Message("TagTopic", "vip2", "我是一个vip2的消息".getBytes())
        );

        SendResult send = producer.send(msgs);
        System.out.println(send);
        // 关闭实例
        producer.shutdown();
    }

    @Test
    public void testTagConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tag-consumer-group");
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        // 订阅一个主题来消费   表达式，默认是*,支持"tagA || tagB || tagC" 这样或者的写法 只要是符合任何一个标签都可以消费
        consumer.subscribe("TagTopic", "vip1 || vip2");
//        consumer.subscribe("TagTopic", "vip1");

        // 注册一个消费监听 MessageListenerConcurrently是并发消费
        // 默认是20个线程一起消费，可以参看 consumer.setConsumeThreadMax()
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            // 这里执行消费的代码 默认是多线程消费
            System.out.println(Thread.currentThread().getName() + "----" + new String(msgs.get(0).getBody()));
            System.out.println(msgs.get(0).getTags());
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
        System.in.read();
    }

}
