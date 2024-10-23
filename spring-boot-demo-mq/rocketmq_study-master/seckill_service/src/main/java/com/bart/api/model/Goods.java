package com.bart.api.model;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author bart
 * @since 2023年07月19日
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Integer id;

    /**
     * 商品名称
     */
    @TableField("goods_name")
    private String goodsName;

    /**
     * 价格
     */
    @TableField("price")
    private BigDecimal price;

    /**
     * 库存
     */
    @TableField("stocks")
    private Integer stocks;

    /**
     * 商品状态 	- 0：停用	- 1：正常	- 2：参与秒杀
     */
    @TableField(value = "status", fill = FieldFill.INSERT)
    private Integer status;

    /**
     * 商品图片
     */
    @TableField("pic")
    private String pic;

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

    public static final String GOODS_NAME = "goods_name";

    public static final String PRICE = "price";

    public static final String STOCKS = "stocks";

    public static final String STATUS = "status";

    public static final String PIC = "pic";

    public static final String VERSION = "version";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_TIME = "update_time";
}
