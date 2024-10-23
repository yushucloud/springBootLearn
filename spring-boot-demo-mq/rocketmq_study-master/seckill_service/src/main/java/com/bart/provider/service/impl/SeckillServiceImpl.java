package com.bart.provider.service.impl;

import com.bart.api.common.CastException;
import com.bart.api.common.Constant;
import com.bart.api.common.Result;
import com.bart.api.common.utils.RedisIdWorker;
import com.bart.api.enums.EnumOrderStatus;
import com.bart.api.model.Goods;
import com.bart.api.model.OrderRecords;
import com.bart.api.view.SeckillView;
import com.bart.provider.service.GoodsService;
import com.bart.provider.service.OrderRecordsService;
import com.bart.provider.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author noobBart
 * @Date 2023/7/22/0022 15:21
 */
@Service
@Slf4j
public class SeckillServiceImpl implements SeckillService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedisIdWorker redisIdWorker;

    @Autowired
    private OrderRecordsService orderRecordsService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    /**
     * 压测时自动是生成用户id
     */
    AtomicInteger ai = new AtomicInteger(0);

    @Override
    public Result<SeckillView> doSeckillGoods(Integer goodsId) {
        Long userId = (long) ai.incrementAndGet();
        //  unique key 唯一标记 去重
        String uk = userId + "-" + goodsId;

        // 用户去重 set nx  set if not exist
        Boolean flag = redisTemplate.opsForValue().setIfAbsent(Constant.USER_IS_SECKILL + uk, "");
        if (!flag) {
            throw new CastException("您已经参与过该商品的抢购，请参与其他商品抢购!");
        }

        // 判断商品是否存在
        // TODO：可以将商品数据放到 redis 中
        Goods goods = goodsService.getById(goodsId);
        if (ObjectUtils.isEmpty(goods)) {
            throw new CastException("商品不存在!");
        }

        // 获取 redis 中库存信息,预扣减库存
        Long stock = redisTemplate.opsForValue().decrement(Constant.GOODS_STOCK + goods.getId());
        log.info("商品id为 {} 的库存为 {}", goodsId, stock);
        if (stock < 0) {
            throw new CastException("该商品已经被抢完！");
        }

        // 创建预订单
        OrderRecords orderRecords = new OrderRecords();
        long orderId = redisIdWorker.nextId(Constant.GOODS_ORDER_ID);
        orderRecords
                .setId(orderId)
                .setGoodsId(goodsId)
                .setUserId(userId);
        boolean save = orderRecordsService.save(orderRecords);
        if (!save) {
            redisTemplate.opsForValue().increment(Constant.GOODS_STOCK + goods.getId());
            throw new CastException("创建订单失败，请重新下单！");
        }


        // 放入 mq，延迟消息 1min
        Message<Long> delayMessage = MessageBuilder.withPayload(orderId).build();
        // 发送一个延时消息，延迟等级为4级，也就是30s后被监听消费
        // messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
        SendResult delayResult = rocketMQTemplate.syncSend(Constant.SECKILL_TOPIC, delayMessage, 2000, 5);
        if (SendStatus.SEND_OK.equals(delayResult.getSendStatus())) {
            SeckillView seckillView = new SeckillView();
            seckillView.setUserId(userId)
                    .setOrderId(orderId)
                    .setOrderStatus(orderRecords.getOrderStatus())
                    .setCreatedTime(orderRecords.getCreateTime());
            return Result.success(seckillView);
        } else {
            redisTemplate.opsForValue().increment(Constant.GOODS_STOCK + goods.getId());
            // 人工？
            orderRecords.setOrderStatus(EnumOrderStatus.订单失效.getCode());
            boolean updatedById = orderRecordsService.updateById(orderRecords);
            if (!updatedById) {
                log.error("更新订单状态失效失败！");
            }
            throw new CastException("创建订单失败，请重新下单！");
        }

    }
}
