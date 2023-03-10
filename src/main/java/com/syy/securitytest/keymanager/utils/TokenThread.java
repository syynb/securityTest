package com.syy.securitytest.keymanager.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.syy.securitytest.keymanager.entity.TokenEntity;
import com.syy.securitytest.keymanager.mapper.TokenMapper;
import com.syy.securitytest.keymanager.service.KeyService;

import java.util.Date;

public class TokenThread extends Thread{
    private TokenMapper mapper;

    private LambdaQueryWrapper<TokenEntity> lambdaQueryWrapper;

    public TokenThread(){}

    public TokenThread(TokenMapper mapper,LambdaQueryWrapper<TokenEntity> lambdaQueryWrapper){
        this.mapper = mapper;
        this.lambdaQueryWrapper = lambdaQueryWrapper;
    }

    @Override
    public void run(){
        try {
            sleep(6000001);
            mapper.delete(lambdaQueryWrapper);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
