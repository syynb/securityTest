package com.syy.securitytest.keymanager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.syy.securitytest.dto.R;
import com.syy.securitytest.dto.UserDto;
import com.syy.securitytest.keymanager.entity.RootKeyEntity;
import com.syy.securitytest.keymanager.entity.TokenEntity;
import com.syy.securitytest.keymanager.mapper.RootKeyMapper;
import com.syy.securitytest.keymanager.mapper.TokenMapper;
import com.syy.securitytest.keymanager.service.KeyService;
import com.syy.securitytest.keymanager.utils.GenerateKey;
import com.syy.securitytest.keymanager.utils.IpTest;
import com.syy.securitytest.keymanager.utils.JYEncryptor;
import com.syy.securitytest.keymanager.utils.TokenThread;
import com.syy.securitytest.user.UserEntity;
import com.syy.securitytest.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class KerServiceImpl implements KeyService {

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
        return new R(200,"更新成功，请重新对输入明文密钥或密码加密",dto);
    }

    @Override
    public R deleteKey(UserDto dto) {
        LambdaQueryWrapper<RootKeyEntity> la = new LambdaQueryWrapper<>();
        la.eq(RootKeyEntity::getUserId,dto.getId());
        Long i = rootKeyMapper.selectCount(la);
        if (i>1){
            return new R(500,"系统错误");
        }
        if (i<1){
            return new R(500,"当前用户无密钥");
        }
        int j = rootKeyMapper.delete(la);
        if (j<1){
            return new R(500,"删除失败");
        }
        dto.setRootKey(null);
        return new R(200,"成功",dto);
    }

    @Override
    public R encrypt(UserDto dto) {
        LambdaQueryWrapper<RootKeyEntity> la = new LambdaQueryWrapper<>();
        la.eq(RootKeyEntity::getUserId,dto.getId());
        List<RootKeyEntity> list = rootKeyMapper.selectList(la);
        if (list==null || list.size()<1){
            return new R(500,"当前用户无密钥");
        }
        if (list.size()>1){
            return new R(500,"系统错误");
        }
        String re = JYEncryptor.getEncode(list.get(0).getRootKey(),dto.getMessage());
        return new R(200,"成功",re);
    }

    @Override
    public String sendKey(String token, HttpServletRequest request) {
        String ip = request.getLocalAddr();
        LambdaQueryWrapper<TokenEntity> la = new LambdaQueryWrapper<>();
        la.eq(TokenEntity::getToken,token);
        List<TokenEntity> entities = tokenMapper.selectList(la);
        if (entities==null || entities.size()!=1){
            return null;
        }
        if (!entities.get(0).getUserIp().equals(ip)){
            return null;
        }
        if(!String.valueOf((entities.get(0).getUserIp()+entities.get(0).getUserId()).hashCode()).equals(token)){
            return null;
        }
        LambdaQueryWrapper<RootKeyEntity> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RootKeyEntity::getUserId,entities.get(0).getUserId());
        List<RootKeyEntity> keys = rootKeyMapper.selectList(lambdaQueryWrapper);
        if (keys==null || keys.size()!=1){
            return null;
        }
        return keys.get(0).getRootKey();
    }

    @Override
    public R getToken(UserDto dto,HttpServletRequest request) {
        LambdaQueryWrapper<RootKeyEntity> laKey = new LambdaQueryWrapper<>();
        laKey.eq(RootKeyEntity::getUserId,dto.getId());
        List<RootKeyEntity> keyList = rootKeyMapper.selectList(laKey);
        if (keyList==null || keyList.size()!=1){
            return new R(500,"当前用户无密钥");
        }
        if(!IpTest.isIpLegal(dto.getUserIp())){
            return new R(500,"ip格式不正确");
        }
        String token = String.valueOf((dto.getId()+dto.getUserIp()).hashCode());
        String message = "请求url:http://"+request.getLocalAddr()+":8888/key/getToken";
        TokenEntity entity = new TokenEntity();
        entity.setToken(token);
        entity.setUserId(dto.getId());
        entity.setUserIp(dto.getUserIp());
        LambdaQueryWrapper<TokenEntity> laTo = new LambdaQueryWrapper<>();
        laTo.eq(TokenEntity::getUserId,dto.getId());
        List<TokenEntity> list = tokenMapper.selectList(laTo);
        if (list!=null && list.size()>1){
            LambdaQueryWrapper<TokenEntity> delWrapper = new LambdaQueryWrapper<>();
            delWrapper.eq(TokenEntity::getId,list.get(0).getId());
            int i = tokenMapper.delete(delWrapper);
            if (i<1){
                return new R(500,"系统错误");
            }
        }
        int i = tokenMapper.insert(entity);
        if(i<1){
            return new R(500,"获取token失败");
        }
        LambdaQueryWrapper<TokenEntity> l = new LambdaQueryWrapper<>();
        l.eq(TokenEntity::getUserId,dto.getId());
        Integer id = tokenMapper.selectList(l).get(0).getId();
        l.eq(TokenEntity::getId,id);
        TokenThread tokenThread = new TokenThread(tokenMapper,l);
        tokenThread.start();

        return new R(200,message,"token(10分钟内失效):"+token);

    }

}
