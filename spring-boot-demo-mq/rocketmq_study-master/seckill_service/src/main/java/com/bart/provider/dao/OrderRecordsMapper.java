package com.bart.provider.dao;

import com.bart.api.model.OrderRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 订单记录表 Mapper 接口
 * </p>
 *
 * @author bart
 * @since 2023年07月21日
 */
@Mapper
public interface OrderRecordsMapper extends BaseMapper<OrderRecords> {

}
