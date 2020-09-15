package com.example.demo.domain.entity;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 会员表
 * </p>
 *
 * @author wzh
 * @since 2020-07-27
 */
@Data
@Accessors(chain = true)
@TableName("member")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;
    //主键自增
    @TableId(value = "mid", type = IdType.AUTO)
    private Long mid;
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 用户昵称
     */
    private String nickName;
    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 角色0 会员 1供应商
     */
    private Integer role;

    /**
     * 分成比例 %
     */
    private String share;

    /**
     * 头像
     */
    private String image;

    /**
     * 简介
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 是否审批 0 待审批 1审批
     */
    private Integer isApproval;

    /**
     * 是否冻结0未冻结 1冻结
     */
    private Integer isFrozen;

    /**
     * 是否删除 0未删除 1已删除
     */
    @TableLogic
    private Integer isDelete;
    //验证码(邮箱验证码或手机号验证码)
    @TableField(exist = false)
    private String code;
    //一个用户对应多个订单【一对多】
    @TableField(exist = false)
    private List<Orders> ordersList;
    //用户和购物车【一对一】
    @TableField(exist = false)
    private ShopCart shopCart;
}
