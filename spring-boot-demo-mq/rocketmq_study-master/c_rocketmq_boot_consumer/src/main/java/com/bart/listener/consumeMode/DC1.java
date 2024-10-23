package com.bart.listener.consumeMode;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 负载均衡模式
 * 消费端集群化部署，每条消息只需要被处理一次。 由于消费进度在服务端维护，可靠性更高。
 * 负载均衡（集群）消费模式下，每一条消息都只会被分发到一台机器上处理。如果需要被集群下的每一台机器都处理，请使用广播模式。
 * 集群消费模式下，不保证每一次失败重投的消息路由到同一台机器上，因此处理消息时不应该做任何确定性假设。
 * @Author noobBart
 * @Date 2023/7/11/0011 21:14
 */
@Component
@RocketMQMessageListener(topic = "modelTopic",
        consumerGroup = "mode-consumer-group-a",
        messageModel = MessageModel.CLUSTERING // 负载均衡（集群模式）
)
public class DC1 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("我是 mode-consumer-group-a 组的第一个消费者：" + message);
    }
}
