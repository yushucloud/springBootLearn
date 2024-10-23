package com.bart.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bart.api.model.OrderRecords;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bart
 * @since 2023年07月16日
 */
public interface OrderRecordsService extends IService<OrderRecords> {

    /**
     * 更新订单状态
     *
     * @param orderId
     * @param orderStatus
     * @return
     */
    boolean updateOrderStatus(Long orderId, Integer orderStatus);

    void orderTimeout(OrderRecords orderRecords);
}
