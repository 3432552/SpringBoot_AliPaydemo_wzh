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
 * 产品表
 * </p>
 *
 * @author wzh
 * @since 2020-07-27
 */
@Data
@Accessors(chain = true)
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "pid", type = IdType.AUTO)
    private Long pid;
    /**
     * 商品名称
     */
    private String name;

    /**
     * 商品简介
     */
    private String description;

    /**
     * 商品视频url
     */
    private String videoUrl;

    /**
     * 商品图片url
     */
    private String imgUrl;

    /**
     * 商品价格
     */
    private String price;

    /**
     * 权重用作排序
     */
    private Integer weight;

    /**
     * 是否上下架 0下架 1上架 2待上架
     */
    private Integer isLoading;

    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loadStartTime;

    /**
     * 下架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date loadEndTime;

    /**
     * 是否审批 0待审批 1审批
     */
    private Integer isApproval;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 供应商id
     */
    private Integer supplierId;

    /**
     * 0未删除 1已删除
     */
    private Integer isDeleted;

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
    //哪个供货商上传的商品
    @TableField(exist = false)
    private Member member;

}
