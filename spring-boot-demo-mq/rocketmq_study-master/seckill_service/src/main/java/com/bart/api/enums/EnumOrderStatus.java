package com.bart.api.enums;

import cn.hutool.core.util.StrUtil;
import com.bart.api.common.CastException;
import lombok.Getter;

import java.util.Objects;

/**
 * @Author noobBart
 * @Date 2023/7/22/0022 9:55
 */
@Getter
public enum EnumOrderStatus {
    订单失效(-1, "订单失效"),
    订单取消(0, "订单取消"),
    订单已完成(1, "订单已完成"),
    订单未支付(2, "订单未支付"),
    订单已支付(3, "订单已支付");

    private Integer code;
    private String name;

    EnumOrderStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 根据订单状态编码获取
     * @param code
     * @return
     */
    public static EnumOrderStatus getOrderStatusByCode(Integer code) {
        for (EnumOrderStatus enumOrderStatus: EnumOrderStatus.values()) {
            if (Objects.equals(code, enumOrderStatus.getCode())) {
                return enumOrderStatus;
            }
        }
        return null;
    }
}
