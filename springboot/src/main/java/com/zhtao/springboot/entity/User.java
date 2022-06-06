package com.zhtao.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhtao
 * @since 2022-06-04
 */

@Data
@TableName("sys_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Alias("ID")
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @Alias("用户名")
    private String username;
    @Alias("密码")
    private String password;
    @Alias("昵称")
    private String nickname;
    @Alias("邮箱")
    private String email;
    @Alias("电话")
    private String phone;
    @Alias("地址")
    private String address;
    @Alias("创建时间")
    private LocalDateTime createTime;
    @Alias("头像")
    private String avatarUrl;


}
