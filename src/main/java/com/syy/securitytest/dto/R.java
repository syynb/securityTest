package com.syy.securitytest.dto;

import lombok.Data;

/**
 * 前端request类
 */
@Data
public class R<T> {
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回消息体
     */
    private T body;

    public R(){}

    public R(int code,String message){
        this.code = code;
        this.message = message;
    }


    public R(int code,String message,T body){
        this.code = code;
        this.message = message;
        this.body = body;
    }


}
