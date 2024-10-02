package com.yushu.orm.mybatis.plus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.yushu.orm.mybatis.plus.config.base.BaseEntity;
import lombok.*;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author MrWen
 * @since 2021-12-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("t_admin")
//@ApiModel(value = "Admin对象", description = "管理员表")
public class Admin extends BaseEntity {

     @TableId(value = "id", type = IdType.AUTO)
    private Long id;

     @TableField("user_name")
    private String userName;

     @TableField("avatar")
    private String avatar;


}
