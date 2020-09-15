package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author wzh
 * @since 2020-07-27
 */
@Data
@Accessors(chain = true)
@TableName("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 支付方式:0.支付宝 1.微信
     */
    private Integer payType;

    /**
     * 订单状态 1待支付 2已支付 3已退款 4已取消 5待退款
     */
    private Integer orderStatus;

    /**
     * 返利状态 1待到账 2已到账
     */
    private Integer agencyStatus;

    /**
     * 返利时间
     */
    private LocalDateTime rebateTime;

    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 订单总金额
     */
    private BigDecimal totalMoney;

    /**
     * 实付金额
     */
    private BigDecimal actualMoney;

    /**
     * 返利比例
     */
    private BigDecimal rebateRate;

    /**
     * 对账状态(标识前后端金额是否一致): 1.正常  2.异常
     */
    private Integer reconcileStatus;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 一个订单对应多个订单详情【一对多的关系】
     */
    @TableField(exist = false)
    private List<OrderInfo> orderInfoList;
}
