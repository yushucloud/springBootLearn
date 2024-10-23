package com.bart.api.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bart.api.common.Constant;
import com.bart.api.model.Goods;
import com.bart.provider.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 数据同步 redis
 *
 * @Author noobBart
 * @Date 2023/7/17/0017 21:58
 */
@Component
@Slf4j
public class DataSyncConfig {
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * spring bean的生命周期
     * 在当前对象 实例化完以后
     * 属性注入以后
     * 执行 PostConstruct 注解的方法
     *
     * @Scheduled：业务场景是搞一个定时任务 每天10点开启
     * @PostConstruct：项目启动就执行一次
     */
    @PostConstruct
    @Scheduled(cron = "0 10 0 0 0 ?")
    private void initData() {
        QueryWrapper<Goods> wrapper = new QueryWrapper<>();
        wrapper.eq(Goods.STATUS, 2).select(Goods.ID, Goods.STOCKS);
        List<Goods> goodsList = goodsService.list(wrapper);
        if (CollectionUtils.isEmpty(goodsList)) {
            return;
        }
        goodsList.forEach(goods -> {
            redisTemplate.opsForValue().set(Constant.GOODS_STOCK + goods.getId(), goods.getStocks().toString());
            log.info("goodId:{}, stocks:{}", goods.getId(), goods.getStocks().toString());
        });
    }

}
