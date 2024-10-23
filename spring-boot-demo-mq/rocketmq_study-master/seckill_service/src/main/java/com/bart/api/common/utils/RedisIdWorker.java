package com.bart.api.common.utils;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * id 生成器
 * 时间戳 + 序列号
 *
 * @Author noobBart
 * @Date 2023/7/16/0016 12:35
 */
@Component
public class RedisIdWorker {
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 开始时间戳：2022-1-1 8:0:0
     */
    private static final Long BEGIN_TIMESTAMP = 1640995200L;

    /**
     * 序列号的位数
     */
    private static final int COUNT_BITS = 32;

    public RedisIdWorker(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public long nextId(String keyPrefix) {
        // 1.生成时间戳
        LocalDateTime now = LocalDateTime.now();
        // 世界标准时间的当前时间戳
        long nowSecond = now.toEpochSecond(ZoneOffset.UTC);
        // 从 2022-1-1 8:0:0 到当前时间的时间戳
        long timestamp = nowSecond - BEGIN_TIMESTAMP;

        // 2.生成序列号
        // 2.1.获取当前日期，精确到天
        String date = now.format(DateTimeFormatter.ofPattern("yyyy:MM:dd"));
        // 2.2.自增长
        long count = stringRedisTemplate.opsForValue().increment("icr:" + keyPrefix + ":" + date);

        // 3.拼接并返回，左移 COUNT_BITS 位
        return timestamp << COUNT_BITS | count;
    }
}
