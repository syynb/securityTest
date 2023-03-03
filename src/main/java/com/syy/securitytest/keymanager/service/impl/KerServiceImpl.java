package com.syy.securitytest.keymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.syy.securitytest.dto.R;
import com.syy.securitytest.dto.UserDto;
import com.syy.securitytest.keymanager.entity.RootKeyEntity;
import com.syy.securitytest.keymanager.mapper.RootKeyMapper;
import com.syy.securitytest.keymanager.mapper.TokenMapper;
import com.syy.securitytest.keymanager.service.KeyService;
import com.syy.securitytest.keymanager.utils.GenerateKey;
import com.syy.securitytest.user.UserEntity;
import com.syy.securitytest.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KerServiceImpl implements KeyService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RootKeyMapper rootKeyMapper;
    @Autowired
    private TokenMapper tokenMapper;


    @Override
    public R generateKey(UserDto dto) {
        //判断是否存在
        LambdaQueryWrapper<RootKeyEntity> la = new LambdaQueryWrapper<>();
        la.eq(RootKeyEntity::getUserId,dto.getId());
        Long i = rootKeyMapper.selectCount(la);
        if (i>0){
            return new R(500,"已存在密钥！");
        }
        //生成密钥
        String key = GenerateKey.generateKey();
        RootKeyEntity rootKeyEntity = new RootKeyEntity();
        rootKeyEntity.setUserId(dto.getId());
        rootKeyEntity.setRootKey(key);
        //插入
        int j = rootKeyMapper.insert(rootKeyEntity);
        if (j<1){
            return new R(500,"系统错误");
        }else {
            UserDto re = new UserDto();
            re.setId(dto.getId());
            re.setRootKey(key);
            re.setUserName(dto.getUserName());
            return new R(200,"创建成功",re);
        }
    }

    @Override
    public R updateKey(UserDto dto) {
        LambdaQueryWrapper<RootKeyEntity> la = new LambdaQueryWrapper<>();
        la.eq(RootKeyEntity::getUserId,dto.getId());
        Long i = rootKeyMapper.selectCount(la);
        if (i!=1){
            return new R(500,"系统错误");
        }
        LambdaUpdateWrapper<RootKeyEntity> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(RootKeyEntity::getUserId,dto.getId());
        String newKey = GenerateKey.generateKey();
        updateWrapper.set(RootKeyEntity::getRootKey,newKey);
        int j = rootKeyMapper.update(null,updateWrapper);
        if (j<1){
            return new R(500,"更新失败");
        }
        dto.setRootKey(newKey);
        return new R(200,"成功",dto);
    }

    @Override
    public R deleteKey(UserDto dto) {
        LambdaQueryWrapper<RootKeyEntity> la = new LambdaQueryWrapper<>();
        la.eq(RootKeyEntity::getUserId,dto.getId());
        Long i = rootKeyMapper.selectCount(la);
        if (i!=1){
            return new R(500,"系统错误");
        }
        int j = rootKeyMapper.delete(la);
        if (j<1){
            return new R(500,"删除失败");
        }
        dto.setRootKey(null);
        return new R(200,"成功",dto);
    }

    @Override
    public R encrypt(String message) {
        return null;
    }

    @Override
    public R sendKey(UserDto dto) {
        return null;
    }
}
