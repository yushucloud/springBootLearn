package com.bart.messageType;

import com.bart.common.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 延迟消息
 *
 * @Author noobBart
 * @Date 2023/7/02/0002 10:17
 */
public class DelayMessageTest {
    @Test
    public void testDelayProducer() throws Exception {
        // 1.创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("delay-producer-group");
        // 2.指定Nameserver地址
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        // 3.启动producer
        producer.start();
        // 4.创建消息对象，指定主题Topic、Tag和消息体等
        Message message = new Message("TopicDelay", "延迟消息".getBytes());
        // 给这个消息设定一个延迟等级
        // messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        message.setDelayTimeLevel(3);

        // 5.发送消息
        producer.send(message);
        // 打印时间
        System.out.println("发送消息：" + LocalDateTime.now());
        // 关闭实例
        producer.shutdown();
    }

    /**
     * 发送消息：2023-07-02T17:07:23.774
     * 收到了消息：2023-07-02T17:07:33.748
     * @throws Exception
     */
    @Test
    public void testDelayConsumer() throws Exception {
        // 1.创建消费者consumer，制定消费者组名
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("delay-consumer-group");
        // 2.指定Nameserver地址
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        // 3.订阅一个主题来消费 *：表示没有过滤参数，表示这个主题的任何消息
        consumer.subscribe("TopicDelay", "*");
        // 4.创建监听订阅主题Topic和Tag等：处理消息
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) ->{
            System.out.println("收到了消息：" + LocalDateTime.now());
            System.out.println("消费内容：" + new String(list.get(0).getBody()));

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        // 5.启动消费者consumer
        consumer.start();
        System.in.read();
    }
}
