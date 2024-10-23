package com.bart.provider.mq.listener;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.bart.api.common.Constant;
import com.bart.api.enums.EnumOrderStatus;
import com.bart.api.model.OrderRecords;
import com.bart.provider.service.OrderRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @Author noobBart
 * @Date 2023/7/22/0022 10:15
 */

/**
 * 时间 14:27
 * 默认负载均衡模式
 * 默认多线程消费
 */
@Component
@RocketMQMessageListener(topic = Constant.SECKILL_TOPIC, consumerGroup = Constant.SECKILL_ORDER_GROUP)
@Slf4j
public class SeckillOrderListener implements RocketMQListener<Long> {
    @Autowired
    private OrderRecordsService orderRecordsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void onMessage(Long orderId) {
        //判断订单是否存在
        if (orderId == null) {
            log.error("RocketMQ Listener::订单 id 为空！");
            return;
        }

        OrderRecords orderRecords = orderRecordsService.getById(orderId);
        if (ObjectUtil.isEmpty(orderRecords)) {
            log.error("RocketMQ Listener::订单不存在！");
            return;
        }

        Integer goodsId = orderRecords.getGoodsId();

        // 自旋锁  一般 mysql 每秒1500/s写   看数量 合理的设置自旋时间  todo 答案3
        int current = 0;
        while (current <= Constant.SPIN_TIME) {
            // 一般在做分布式锁的情况下  会给锁一个过期时间 防止出现死锁的问题
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(Constant.GOODS_LOCK + goodsId, "", 10, TimeUnit.SECONDS);
            if (flag) {
                try {
                    orderRecordsService.orderTimeout(orderRecords);
                    return;
                } finally {
                    redisTemplate.delete(Constant.GOODS_LOCK + goodsId);
                }
            } else {
                current += 200;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
