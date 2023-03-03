package com.syy.securitytest.keymanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.syy.securitytest.keymanager.entity.TokenEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapper extends BaseMapper<TokenEntity> {
}
