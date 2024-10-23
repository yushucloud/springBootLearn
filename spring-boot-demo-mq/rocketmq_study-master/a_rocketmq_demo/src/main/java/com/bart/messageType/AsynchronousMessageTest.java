package com.bart.messageType;

import com.bart.common.MqConstant;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;

/**
 * 异步消息
 *
 * @Author noobBart
 * @Date 2023/7/01/0001 23:29
 */
public class AsynchronousMessageTest {
    @Test
    public void testAsyncProducer() throws Exception {
        // 1.创建默认消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("async-producer-group");

        // 2.指定Nameserver地址
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        // 3.启动producer
        producer.start();

        // 4.创建消息对象，指定主题Topic、Tag和消息体等
        Message message = new Message("asyncTopic", "异步消息".getBytes());

        // 5.发送异步消息
        producer.send(message, new SendCallback() {
            /**
             * 发送成功后回调
             * @param sendResult
             */
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功");
            }

            /**
             * 发送失败后回调
             * @param throwable
             */
            @Override
            public void onException(Throwable throwable) {
                System.out.println("发送失败");
            }
        });

        System.out.println("看看谁先执行"); // 先输出
        // 挂起jvm 因为回调是异步的不然测试不出来
        System.in.read();
        // 6.关闭生产者producer
        producer.shutdown();
    }

    @Test
    public void testAsyncConsumer() throws Exception {
        // 创建默认消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("async-consumer-group");

        // 设置nameServer地址
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);

        // 订阅一个主题来消费   *表示没有过滤参数 表示这个主题的任何消息
        consumer.subscribe("asyncTopic", "*");

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
