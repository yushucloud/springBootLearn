package com.bart.provider.dao;

import com.bart.api.model.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author bart
 * @since 2023年07月21日
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
    Goods getGoodsById(Integer id);
}
