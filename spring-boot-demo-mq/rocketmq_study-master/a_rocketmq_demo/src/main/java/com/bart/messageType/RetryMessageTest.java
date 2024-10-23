package com.bart.messageType;

import com.bart.common.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * 重试机制
 *
 * @Author noobBart
 * @Date 2023/7/05/0005 20:56
 */
public class RetryMessageTest {
    @Test
    public void retryProducer() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("retry-producer-group");
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        producer.start();
        // 设置发送失败时的重试次数
        producer.setRetryTimesWhenSendFailed(2);
        // 设置发送异步失败时的重试次数
        producer.setRetryTimesWhenSendAsyncFailed(2);

        String key = UUID.randomUUID().toString();
        System.out.println(key);
        Message message = new Message("retryTopic", "vip1", key, "我是一个vip1的消息".getBytes());
        producer.send(message);
        System.out.println("发送成功！");
        producer.shutdown();
    }

    @Test
    public void retryConsumer() throws MQClientException, IOException {
        // 创建默认消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("retry-consumer-group");
        // 设置nameServer地址
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        // 订阅一个主题来消费   *表示没有过滤参数 表示这个主题的任何消息
        consumer.subscribe("retryTopic", "*");
        // 注册一个消费监听
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                try {
                    // 这里执行消费的代码
                    System.out.println(Thread.currentThread().getName() + "----" + msgs);
                    // 这里制造一个错误
                    int i = 10 / 0;
                } catch (Exception e) {
                    // 出现问题 判断重试的次数
                    MessageExt messageExt = msgs.get(0);
                    // 获取重试的次数 失败一次消息中的失败次数会累加一次
                    int reconsumeTimes = messageExt.getReconsumeTimes();
                    System.out.println("---------重试次数：" + reconsumeTimes);
                    if (reconsumeTimes >= 3) {
                        // 则把消息确认了，可以将这条消息记录到日志或者数据库 通知人工处理
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } else {
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }

}
