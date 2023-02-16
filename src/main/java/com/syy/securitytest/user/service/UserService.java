package com.syy.securitytest.user.service;

import com.syy.securitytest.dto.R;
import com.syy.securitytest.user.UserEntity;

public interface UserService {

    /**
     * 新建用户
     */
    public R insertUser(UserEntity user);

    /**
     * 删除用户
     */
    public R deleteUser(String userName);

    /**
     * 更新用户信息
     */
    public R updateUser(UserEntity user);

    /**
     * 查询用户信息
     */
    public R selectUser(String userName);

    /**
     * 登录
     */
    public R login(UserEntity entity);


    /**
     * 注册
     */
    public R register(UserEntity entity);
}
