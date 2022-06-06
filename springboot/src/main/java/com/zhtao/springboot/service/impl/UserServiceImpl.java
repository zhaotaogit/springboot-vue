package com.zhtao.springboot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhtao.springboot.common.Constants;
import com.zhtao.springboot.controller.dto.UserDto;
import com.zhtao.springboot.entity.User;
import com.zhtao.springboot.exception.ServiceException;
import com.zhtao.springboot.mapper.UserMapper;
import com.zhtao.springboot.service.IUserService;
import com.zhtao.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhtao
 * @since 2022-06-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final Log LOG = Log.get();

    @Override
    public UserDto login(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one != null) {
            BeanUtil.copyProperties(one, userDto, true); // 将one复制到userDto,忽略大小写
            String token = TokenUtils.generateToken(one.getId().toString(),one.getPassword());
            userDto.setToken(token);
            return userDto;
        }else {
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误！" );
        }
    }

    @Override
    public User register(UserDto userDto) {
        User one = getUserInfo(userDto);
        if (one==null){
            one = new User();
            BeanUtil.copyProperties(userDto,one);
            save(one);
            return one;
        }
        throw new ServiceException(Constants.CODE_600,"用户已存在!" );

    }


    private User getUserInfo(UserDto userDto){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername()).eq("password", userDto.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        }catch (Exception e){
            LOG.error(e);
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        return one;
    }
}
