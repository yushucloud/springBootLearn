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
 * 同步消息
 *
 * @Author noobBart
 * @Date 2023/7/01/0001 16:15
 */
public class SynchronousMessageTest {
    @Test
    public void testProducer() throws Exception {
        // 1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("test-producer-group");

        // 2.指定Nameserver地址
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        // 3.启动producer
        producer.start();

        // 4.创建消息对象，指定主题Topic、Tag和消息体等
        for (int i = 0; i < 10; i++) {
            Message message = new Message(MqConstant.TOPIC_TEST_NAME, ("测试消息" + i).getBytes());
            // 5.发送消息
            SendResult result = producer.send(message);
            System.out.println("发送结果状态：" + result.getSendStatus());
        }


        // 6.关闭生产者producer
        producer.shutdown();
    }


    @Test
    public void testConsumer() throws Exception {
        // 1.创建消费者consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("test-consumer-group");

        // 2.指定Nameserver地址
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        System.out.println("-----------:" + consumer.getClientIP());
        // 3.订阅一个主题来消费 *：表示没有过滤参数，表示这个主题的任何消息
        consumer.subscribe("TopicTest", "*");

        // 4.创建监听订阅主题Topic和Tag等
        // 处理消息（业务处理）
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            System.out.println("我是消费者");
            System.out.println(list.get(0).toString());
            System.out.println("消费内容：" + new String(list.get(0).getBody()));
            System.out.println("消费上下文：" + consumeConcurrentlyContext);

            // 返回消费的状态 如果是 CONSUME_SUCCESS 则成功，若为 RECONSUME_LATER 则该条消息会被重回队列，重新被投递
            // 重试的时间为 messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
            // 也就是第一次1s 第二次5s 第三次10s  ....  如果重试了18次 那么这个消息就会被终止发送给消费者
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        });

        // 5.启动消费者consumer
        consumer.start();

        // 6.挂起当前 JVM
        System.in.read();
    }

}
