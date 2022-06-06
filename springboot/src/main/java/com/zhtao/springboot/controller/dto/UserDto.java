package com.zhtao.springboot.controller.dto;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

/**
 * @Author ZhTao
 * @PackageName:com.zhtao.springboot.controller.dto
 * @ClassName: UserDto
 * @Date 2022/6/5 20:07
 */


/**
 * 接收前端登录传入的数据
 */
@Data
public class UserDto {
    @Alias("用户名")
    private String username;
    @Alias("密码")
    private String password;
    @Alias("昵称")
    private String nickname;
    @Alias("头像")
    private String avatarUrl;
    private String token;

}
