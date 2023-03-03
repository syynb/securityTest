package com.syy.securitytest.user.service;

import com.syy.securitytest.dto.R;
import com.syy.securitytest.dto.UserDto;
import com.syy.securitytest.user.UserEntity;

import java.util.List;

public interface UserService {

    /**
     * 新建用户
     */
    public R insertUser(UserDto user);

    /**
     * 删除用户
     */
    public R deleteUser(String userName);

    /**
     * 更新用户信息
     */
    public R updateUser(UserDto user);

    /**
     * 查询用户信息
     */
    public R selectUser(List<String> userName);

    /**
     * 登录
     */
    public R login(UserDto entity);


    /**
     * 注册
     */
    public R register(UserDto entity);
}
