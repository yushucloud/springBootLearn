package com.bart;

import com.bart.model.Order;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class BRocketmqBootProducerApplicationTests {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Test
    public void bootProducerTest() {
        // 同步消息
//        SendResult sendResult = rocketMQTemplate.syncSend("bootTestTopic", "我是一条boot的消息");
//        System.out.println(sendResult.toString());
//
//        System.out.println("----------------------");
//        // 异步消息
//        rocketMQTemplate.asyncSend("bootAsyncTopic", "异步消息", new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                System.out.println("异步消息发送成功");
//            }
//
//            @Override
//            public void onException(Throwable throwable) {
//                System.out.println("异步消息发送失败");
//            }
//        });
//        System.out.println("谁先执行");
//
//        System.out.println("----------------------");
//        // 单向消息
//        rocketMQTemplate.sendOneWay("bootOneWayTopic", "单向消息");
//
//        System.out.println("----------------------");
//
//        // 延迟消息
//        // 构建消息对象
//        Message<String> delayMessage = MessageBuilder.withPayload("延迟消息").build();
//        // 发送一个延时消息，延迟等级为4级，也就是30s后被监听消费
//        SendResult delayResult = rocketMQTemplate.syncSend("bootDelayTopic", delayMessage, 2000, 4);
//        System.out.println(delayResult.getSendStatus());

        System.out.println("----------------------");
        // 顺序消息
        List<Order> orderList = Arrays.asList(new Order(1, 111, 59D, LocalDateTime.now(), "下订单", 1), new Order(2, 111, 59D, LocalDateTime.now(), "物流", 2), new Order(3, 111, 59D, LocalDateTime.now(), "签收", 3), new Order(4, 112, 89D, LocalDateTime.now(), "下订单", 1), new Order(5, 112, 89D, LocalDateTime.now(), "物流", 2), new Order(6, 112, 89D, LocalDateTime.now(), "拒收", 3));
        // 我们控制流程为 下订单->物流->签收  hash的值为seq，也就是说 seq相同的会放在同一个队列里面，顺序消费
        orderList.forEach(order -> {
            SendResult orderlyResult = rocketMQTemplate.syncSendOrderly("bootOrderlyTopic", order, String.valueOf(order.getSeq()));
            System.out.println("订单号：" + order.getOrderId() + ": " + orderlyResult.getSendStatus());
        });


//        System.out.println("----------------------");
//        // 批量消息
//        List<org.apache.rocketmq.common.message.Message> msgs = Arrays.asList(
//                new org.apache.rocketmq.common.message.Message("bootBatchTopic", "我是一组消息的A消息".getBytes()),
//                new org.apache.rocketmq.common.message.Message("bootBatchTopic", "我是一组消息的B消息".getBytes()),
//                new org.apache.rocketmq.common.message.Message("bootBatchTopic", "我是一组消息的C消息".getBytes())
//
//        );
//        SendResult batchResult = rocketMQTemplate.syncSend("bootBatchTopic", msgs);
//        System.out.println(batchResult.getSendStatus());


        System.out.println("----------------------");
        // 带 tags 的消息


        System.out.println("----------------------");
        // 带 key 的消息
    }

    /**
     * 发送一个带tag的消息
     */
    @Test
    public void testTagMsg() {
        // 发送一个 tag 为 TagA 的数据
        rocketMQTemplate.syncSend("bootTapTopic:TagA", "我是一个带tag的消息");
    }

    /**
     * 发送一个带key的消息,我们使用事务消息 打断点查看消息头
     *
     * @throws Exception
     */
    @Test
    public void testKeyMsg() throws Exception {
        // 发送一个key为spring的事务消息
        Message<String> message = MessageBuilder.withPayload("我是一个带key的消息")
                .setHeader(RocketMQHeaders.KEYS, "spring")
                .build();

        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction("bootKeyTopic", message, "我是一个带key的消息");
        System.out.println(transactionSendResult.getSendStatus());
    }

    /**
     * 测试消息消费的模式
     *
     * @throws Exception
     */
    @Test
    public void testMsgModel() throws Exception {
        for (int i = 0; i < 6; i++) {
            rocketMQTemplate.syncSend("modelTopic", "我是消息" + i);
        }
    }

}
