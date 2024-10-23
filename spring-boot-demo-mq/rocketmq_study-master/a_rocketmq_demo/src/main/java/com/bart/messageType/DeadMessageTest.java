package com.bart.messageType;

import com.bart.common.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;

/**
 * 死信消息
 *
 * @Author noobBart
 * @Date 2023/7/05/0005 21:50
 */
public class DeadMessageTest {
    @Test
    public void testDeadMsgProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("dead-producer-group");
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        producer.start();
        Message message = new Message("deadTopic", "我是一个死信消息".getBytes());
        SendResult send = producer.send(message);
        System.out.println(send);
        producer.shutdown();
    }

    @Test
    public void testDeadMsgConsumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("dead-consumer-group");
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        consumer.subscribe("deadTopic", "*");
        // 设置最大消费重试次数 2 次
        consumer.setMaxReconsumeTimes(2);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println("----重试次数：" + msgs.get(0).getReconsumeTimes());
                System.out.println(msgs);
                // 测试消费失败
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        });
        consumer.start();
        System.in.read();
    }

    @Test
    public void testDeadMq() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("dead-consumer-group");
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        // 消费重试到达阈值以后，消息不会被投递给消费者了，而是进入了死信队列
        // 队列名称 默认是 %DLQ% + 消费者组名
        consumer.subscribe("%DLQ%dead-consumer-group", "*");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(msgs);
                // 处理消息 签收了
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }

}
