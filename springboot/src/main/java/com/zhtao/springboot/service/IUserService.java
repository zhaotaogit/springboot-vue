package com.zhtao.springboot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhtao.springboot.controller.dto.UserDto;
import com.zhtao.springboot.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhtao
 * @since 2022-06-04
 */
public interface IUserService extends IService<User> {

    UserDto login(UserDto user);

    User register(UserDto userDto);
}
