package com.syy.securitytest.dto;

import lombok.Data;

/**
 * 前端传的用户信息
 */
@Data
public class UserDto {
    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户密钥
     */
    private String rootKey;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 用户token
     */
    private String token;

    /**
     * 登录token
     */
    private String logToken;

    /**
     * 加密密文
     */
    private String message;
}
