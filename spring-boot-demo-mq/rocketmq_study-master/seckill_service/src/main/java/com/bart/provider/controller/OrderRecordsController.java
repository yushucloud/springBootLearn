package com.bart.provider.controller;

import com.bart.api.common.Result;
import com.bart.provider.service.OrderRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bart
 * @since 2023年07月16日
 */
@RestController
@RequestMapping("/orderRecords")
@Slf4j
public class OrderRecordsController {
    @Autowired
    private OrderRecordsService orderRecordsService;

    /**
     * 更新订单状态
     *
     * @param orderId
     * @return
     */
    @PostMapping("/update/status")
    public Result<Boolean> updateOrderStatus(Long orderId, Integer orderStatus) {
        return Result.success(orderRecordsService.updateOrderStatus(orderId, orderStatus));
    }
}
