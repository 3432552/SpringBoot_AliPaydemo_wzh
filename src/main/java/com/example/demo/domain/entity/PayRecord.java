package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 支付记录表
 * </p>
 *
 * @author wzh
 * @since 2020-07-27
 */
@Data
@Accessors(chain = true)
@TableName("pay_record")
public class PayRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 支付流水号
     */
    private String paySerialNumber;

    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 交易金额
     */
    private BigDecimal money;

    /**
     * 支付方式:0.支付宝 1.微信
     */
    private Integer payType;

    /**
     * 支付状态 0待支付 1成功 2失败
     */
    private Integer payStatus;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
