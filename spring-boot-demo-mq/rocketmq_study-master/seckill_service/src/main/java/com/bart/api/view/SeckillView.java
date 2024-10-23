package com.bart.api.view;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @Author noobBart
 * @Date 2023/7/19/0019 21:54
 */
@Data
@Accessors(chain = true)
public class SeckillView {
    private Long userId; // 用户 id

    private Long orderId; // 订单号

    private LocalDateTime createdTime; // 订单创建时间

    private Integer orderStatus; // 订单状态
}
