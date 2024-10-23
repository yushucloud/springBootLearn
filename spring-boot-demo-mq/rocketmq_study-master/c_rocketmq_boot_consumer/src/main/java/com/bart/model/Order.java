package com.bart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author noobBart
 * @Date 2023/7/03/0003 22:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 订单编号
     */
    private Integer orderNumber;

    /**
     * 订单价格
     */
    private Double price;

    /**
     * 订单号创建时间
     */
    private LocalDateTime createTime;

    /**
     * 订单描述
     */
    private String desc;

    /**
     * 订单的流程顺序
     */
    private Integer seq;

}
