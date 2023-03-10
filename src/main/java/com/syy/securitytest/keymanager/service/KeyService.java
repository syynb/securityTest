package com.syy.securitytest.keymanager.service;

import com.syy.securitytest.dto.R;
import com.syy.securitytest.dto.UserDto;

import javax.servlet.http.HttpServletRequest;

public interface KeyService {

    /**
     * 生成密钥,存入数据库
     * @param dto
     * @return
     */
    public R generateKey(UserDto dto);

    /**
     * 更新密钥
     * @param dto
     * @return
     */
    public R updateKey(UserDto dto);

    /**
     * 删除密钥
     * @param dto
     * @return
     */
    public R deleteKey(UserDto dto);

    /**
     * 加密
     * @param
     * @return
     */
    public R encrypt(UserDto dto);

    /**
     * 向用户发送密钥
     * @param token
     * @return
     */
    public String sendKey(String token, HttpServletRequest request);


    /**
     * 用户创建获取token,10分钟有效期
     */
    public R getToken(UserDto dto,HttpServletRequest request);
}
