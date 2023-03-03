package com.syy.securitytest.keymanager.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.models.auth.In;
import lombok.Data;

import java.io.Serializable;

@TableName("root_key")
@Data
public class TokenEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 用户ip
     */
    private String userIp;

    /**
     * 用户token
     */
    private String token;


}
