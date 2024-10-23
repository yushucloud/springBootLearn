package com.bart.listener;

/**
 * @Author noobBart
 * @Date 2023/7/09/0009 17:12
 */

import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 创建一个简单消息的监听
 * 1.类上添加注解@Component和@RocketMQMessageListener
 *      @RocketMQMessageListener(topic = "powernode", consumerGroup = "powernode-group")
 *      topic指定消费的主题，consumerGroup指定消费组,一个主题可以有多个消费者组,一个消息可以被多个不同的组的消费者都消费
 *      messageModel：消费模式：
 *          ● 负载均衡模式 CLUSTERING：
 *          ● 广播模式 BROADCASTING：消费者组里的消费者都发一遍 msg
 * 2.实现RocketMQListener接口，注意泛型的使用，可以为具体的类型，如果想拿到消息
 * 的其他参数可以写成MessageExt
 */
@Component
@RocketMQMessageListener(topic = "bootTestTopic", consumerGroup = "boot-consumer-group", messageModel = MessageModel.CLUSTERING)
public class ABootSimpleMsgListener implements RocketMQListener<MessageExt> {
    /**
     * 这个方法就是消费者的方法
     * 如果泛型制定了固定的类型那么消息体就是我门的参数
     * MessageExt类型是，消息的所有内容
     *
     * 没有报错就签收了
     * 如果根错了就是拒收就会重试
     * @param messageExt
     */
    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println(messageExt.toString());
    }
}
