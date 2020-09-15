package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author wzh
 * @since 2020-07-27
 */
@Data
@Accessors(chain = true)
@TableName("order_info")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "oid", type = IdType.AUTO)
    private Long oid;
    /**
     * 订单号关联外键
     */
    private String orderId;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 订单备注
     */
    private String remark;
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
    //一个详情对应一个商品【一对一关系】
    @TableField(exist = false)
    private Product product;
}
