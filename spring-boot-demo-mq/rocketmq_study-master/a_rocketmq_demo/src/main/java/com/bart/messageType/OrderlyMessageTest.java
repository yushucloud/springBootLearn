package com.bart.messageType;

import com.bart.common.MqConstant;
import com.bart.model.Order;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 顺序消息
 *
 * @Author noobBart
 * @Date 2023/7/03/0003 22:41
 */
public class OrderlyMessageTest {

    @Test
    public void testOrderlyProducer() throws Exception {
        // 创建默认的生产者
        DefaultMQProducer producer = new DefaultMQProducer("orderly-producer-group");
        // 设置nameServer地址
        producer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        // 启动实例
        producer.start();
        List<Order> orderList = Arrays.asList(
                new Order(1, 111, 59D, LocalDateTime.now(), "下订单", 1),
                new Order(2, 111, 59D, LocalDateTime.now(), "物流", 2),
                new Order(3, 111, 59D, LocalDateTime.now(), "签收", 3),
                new Order(4, 112, 89D, LocalDateTime.now(), "下订单", 1),
                new Order(5, 112, 89D, LocalDateTime.now(), "物流", 2),
                new Order(6, 112, 89D, LocalDateTime.now(), "拒收", 3)
        );
        // 循环集合开始发送
        orderList.forEach(order -> {
            Message message = new Message("orderlyTopic", order.toString().getBytes());
            try {
                // 发送的时候 相同的订单号选择同一个队列
                producer.send(message, new MessageQueueSelector() {
                    @Override
                    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                        // 当前主题有多少个队列
                        int queueNumber = mqs.size();
                        // 这个arg就是后面传入的 order.getOrderNumber()
                        Integer i = (Integer) arg;
                        // 用这个值去%队列的个数得到一个队列
                        int index = i % queueNumber;
                        // 返回选择的这个队列即可 ，那么相同的订单号 就会被放在相同的队列里 实现FIFO了
                        return mqs.get(index);
                    }
                }, order.getOrderNumber());
            } catch (Exception e) {
                System.out.println("发送异常");
            }
        });
        // 关闭实例
        producer.shutdown();
    }

    @Test
    public void testOrderlyConsumer() throws Exception {
        // 创建默认消费者组
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("orderly-consumer-group");
        // 设置nameServer地址
        consumer.setNamesrvAddr(MqConstant.NAME_SRV_ADDR);
        // 订阅一个主题来消费   *表示没有过滤参数 表示这个主题的任何消息
        consumer.subscribe("orderlyTopic", "*");
        // 注册一个消费监听 MessageListenerOrderly 是顺序消费 单线程消费
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                MessageExt messageExt = msgs.get(0);
                System.out.println(new String(messageExt.getBody()));
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.in.read();
    }


}
