package com.bart.api.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单记录表
 * </p>
 *
 * @author bart
 * @since 2023年07月19日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("order_records")
public class OrderRecords implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(value = "id", type = IdType.AUTO)
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;

    @TableField("order_sn")
    private String orderSn;

    /**
     * 商品id
     */
    @TableField("goods_id")
    private Integer goodsId;

    /**
     * 订单状态
     * - -1：订单失效
     * - 0：订单取消
     * - 1：订单已完成
     * - 2：订单未支付
     * - 3：订单已支付
     */
    @TableField(value = "order_status", fill = FieldFill.INSERT)
    private Integer orderStatus;

    /**
     * 版本号
     */
    @TableField(value ="version", fill = FieldFill.INSERT)
    @Version
    private Integer version;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public static final String ID = "id";

    public static final String USER_ID = "user_id";

    public static final String ORDER_SN = "order_sn";

    public static final String GOODS_ID = "goods_id";

    public static final String ORDER_STATUS = "order_status";

    public static final String VERSION = "version";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";
}
