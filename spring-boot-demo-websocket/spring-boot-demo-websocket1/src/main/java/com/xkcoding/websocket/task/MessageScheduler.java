package com.xkcoding.websocket.task;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class MessageScheduler {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageScheduler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 2000) // 每两秒执行一次
    public void sendMessage() {
        String message = "定时消息: " + System.currentTimeMillis();
        messagingTemplate.convertAndSend("/topic/messages", message); // 发送消息到指定的主题
    }
}
