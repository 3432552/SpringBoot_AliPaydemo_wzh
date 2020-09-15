package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 有管理权限的用户
 * </p>
 *
 * @author wzh
 * @since 2020-07-27
 */
@Data
@Accessors(chain = true)
@TableName("web_user")
public class WebUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 管理员登录账户
     */
    private String webUserName;

    /**
     * 管理员密码
     */
    private String webUserPwd;

    /**
     * 管理员名称
     */
    private String webRoleId;

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
     * 是否删除 0未删除 1删除 逻辑删除注解
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 是否冻结 0未冻结 1冻结
     */
    private Integer isFrozen;


}
