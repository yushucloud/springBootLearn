package com.liang.common.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeDTO {

    private String incrId;
    private String reduceId;
    private BigDecimal amount;

}
