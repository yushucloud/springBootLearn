package com.oujiong.websocket.service;

import com.oujiong.websocket.controller.group.User;
import com.oujiong.websocket.model.CoinResult;
import com.oujiong.websocket.model.InMessage;
import com.oujiong.websocket.model.OutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 *
 * 功能描述：简单消息模板，用来推送消息
 */
@Service
public class WebSocketService {


    @Autowired
    private SimpMessagingTemplate template;

    /**
     * 简单点对点聊天室
     */
    public void sendChatMessage(InMessage message) {
        //可以看出template最大的灵活就是我们可以获取前端传来的参数来指定订阅地址
        //前面参数是订阅地址，后面参数是消息信息
        template.convertAndSend("/chat/single/"+message.getTo(),
                new OutMessage(message.getFrom()+" 发送:"+ message.getContent()));
    }



    /**
     * 功能描述：发送在线用户
     */
    public void sendOnlineUser(Map<String, User> onlineUser) {
        String msg = "";
        for(Map.Entry<String, User> entry : onlineUser.entrySet()){
            msg = msg.concat(entry.getValue().getUsername()+"||");
        }
        System.out.println(msg);
        template.convertAndSend("/topic/onlineuser",new OutMessage(msg));
    }

    /**
     * 功能描述： group: 用于多人聊天
     */
    public void sendTopicChat(InMessage message) {
        String msg = message.getFrom() +" 发送:"+message.getContent();
        template.convertAndSend("/topic/chat",new OutMessage(msg));
    }
}