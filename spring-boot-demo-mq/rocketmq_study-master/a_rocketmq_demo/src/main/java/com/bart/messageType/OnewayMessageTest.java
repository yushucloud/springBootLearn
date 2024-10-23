package com.bart.messageType;

import com.bart.common.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;

/**
 * 单向消息
 *
 * @Author noobBart
 * @Date 2023/7/02/0002 0:56
 */
public class OnewayMessageTest {
    @Test
    public void testOnewayProducer() throws Exception {
        // 1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("oneway-producer-group");
        // 2.指定Nameserver地址
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        // 3.启动producer
        producer.start();
        // 4.创建消息对象，指定主题Topic、Tag和消息体等
        Message message = new Message(MqConstant.TOPIC_TEST_NAME, "单向消息：日志xxx".getBytes());
        // 5.发送消息
        producer.sendOneway(message);
        // 6.关闭生产者producer
        producer.shutdown();
    }

    @Test
    public void testOnewayConsumer() throws Exception {
        // 创建默认消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("oneway-consumer-group");

        // 设置nameServer地址
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        // 订阅一个主题来消费   *表示没有过滤参数 表示这个主题的任何消息
        consumer.subscribe("onewayTopic", "*");

        // 注册一个消费监听 MessageListenerConcurrently是并发消费
        // 默认是20个线程一起消费，可以参看 consumer.setConsumeThreadMax()
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                // 这里执行消费的代码 默认是多线程消费
                System.out.println(Thread.currentThread().getName() + "----" + new String(msgs.get(0).getBody()) + "----" + msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        consumer.start();
        System.in.read();
    }
}
