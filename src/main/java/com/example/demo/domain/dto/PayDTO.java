package com.example.demo.domain.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by zy on 2019/8/16.
 * 支付dto
 */
@Data
public class PayDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单id
     */
    private String orderId;

    /**
     * 支付金额（两位小数）
     */
    private BigDecimal totalAmount;

    /**
     * 商品名称
     */
    private String subject;
}
