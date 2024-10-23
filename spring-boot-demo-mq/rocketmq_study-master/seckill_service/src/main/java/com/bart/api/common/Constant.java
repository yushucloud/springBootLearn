package com.bart.api.common;

/**
 * 公共常量
 * @Author noobBart
 * @Date 2023/7/17/0017 22:50
 */
public class Constant {
    /*----------------------- Redis -----------------------*/
    // 用户是否存于该商品的秒杀
    public static final String USER_IS_SECKILL = "seckill:";
    // redis 库存 key
    public static final String GOODS_STOCK = "goods_stock:";

    // 用于生成 订单id
    public static final String GOODS_ORDER_ID = "goods_order_id:";

    // 分布式锁
    public static final String GOODS_LOCK = "goods_lock:";

    /*----------------------- MQ -----------------------*/
    // 订单 topic
    public static final String SECKILL_TOPIC = "seckillTopic:";

    // 订单消费者组
    public static final String SECKILL_ORDER_GROUP = "seckill_order_group";

    /*----------------------- 系统常量 -----------------------*/
    // 自旋时间 20s
    public static final int SPIN_TIME = 20000;

}
