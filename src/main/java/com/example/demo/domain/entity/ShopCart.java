package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author wzh
 * @since 2020-08-17
 * 购物车表
 */
@Data
@Accessors(chain = true)
public class ShopCart implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long memberId;

    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品数量
     */
    private Integer num;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
    //购物车和商品的关系【一对多】
    @TableField(exist = false)
    private List<Product> productList;
}
