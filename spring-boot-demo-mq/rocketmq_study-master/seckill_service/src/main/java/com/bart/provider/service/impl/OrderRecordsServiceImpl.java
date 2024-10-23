package com.bart.provider.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bart.api.common.CastException;
import com.bart.api.common.Constant;
import com.bart.api.enums.EnumOrderStatus;
import com.bart.api.model.OrderRecords;
import com.bart.provider.dao.OrderRecordsMapper;
import com.bart.provider.service.OrderRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bart
 * @since 2023年07月16日
 */
@Service
public class OrderRecordsServiceImpl extends ServiceImpl<OrderRecordsMapper, OrderRecords> implements OrderRecordsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param orderStatus
     * @return
     */
    @Override
    public boolean updateOrderStatus(Long orderId, Integer orderStatus) {
        OrderRecords orderRecords = baseMapper.selectById(orderId);
        orderRecords.setOrderStatus(orderStatus);

        Boolean result = false;
        int i = baseMapper.updateById(orderRecords);
        if(i < 1) {
            log.error(
                    StrUtil.format("更新id为 {} 的订单，状态为 {} 失败！",
                            orderId, EnumOrderStatus.getOrderStatusByCode(orderStatus).getName()));
        } else {
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void orderTimeout(OrderRecords orderRecords) {
        Long orderId = orderRecords.getId();

        String message = null;
        //判断订单状态是否已支付
        if (ObjectUtil.equals(EnumOrderStatus.订单失效.getCode(), orderRecords.getOrderStatus())) {
            message = StrUtil.format("RocketMQ Listener::id 为 {} 的订单已失效！", orderId);
        } else if (ObjectUtil.equals(EnumOrderStatus.订单已完成.getCode(), orderRecords.getOrderStatus())) {
            message = StrUtil.format("RocketMQ Listener::id 为 {} 的订单已完成！", orderId);
        } else if (ObjectUtil.equals(EnumOrderStatus.订单未支付.getCode(), orderRecords.getOrderStatus())) {
            //● 未支付：返还库存，并设置订单状态为失效，将 redis 中该用户是否已参与该商品秒杀
            log.error(StrUtil.format("RocketMQ Listener::id 为 {} 的订单未在规定时间内支付！", orderId));
            redisTemplate.delete(Constant.USER_IS_SECKILL + orderRecords.getUserId() + "-" + orderRecords.getGoodsId());
            orderRecords.setOrderStatus(EnumOrderStatus.订单失效.getCode());
            boolean update = updateById(orderRecords);
            if(!update) {
                message = StrUtil.format("RocketMQ Listener::id 为 {} 的订单更新失效状态失败！", orderId);
            } else {
                return;
            }
        } else if (ObjectUtil.equals(EnumOrderStatus.订单已支付.getCode(), orderRecords.getOrderStatus())) {
            //● 已支付：后续操作？通知发货什么的
            message = StrUtil.format("RocketMQ Listener::id 为 {} 的订单已支付！", orderId);
        } else {
            message = StrUtil.format("RocketMQ Listener::id 为 {} 的订单更新失效状态失败！", orderId);
        }
        if (StrUtil.isNotBlank(message)) {
            log.error(message);
            throw new CastException(message);
        }
    }
}
