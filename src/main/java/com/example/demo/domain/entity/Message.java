package com.example.demo.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户留言
 * </p>
 *
 * @author wzh
 * @since 2020-07-27
 */
@Data
@Accessors(chain = true)
@TableName("message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 消息内容
     */
    private String content;

    /**
     * 手机号
     */
    private String telphone;
    /**
     * 产品外键
     */
    private Long productId;

    /**
     * 发送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date sendTime;

}
