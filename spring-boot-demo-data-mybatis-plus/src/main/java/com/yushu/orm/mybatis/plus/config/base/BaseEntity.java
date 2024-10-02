package com.yushu.orm.mybatis.plus.config.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 基础属性
 * </p>
 *
 * @author MrWen
 **/
@Data
public class BaseEntity {

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT)
    private String createName;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateName;

    @Version
    private Integer version;

    @TableLogic
    @TableField(select = false)
    private Boolean deleted;

    private Integer tenantId;
}
