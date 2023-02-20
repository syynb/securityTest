package com.syy.securitytest.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.syy.securitytest.dto.R;
import com.syy.securitytest.user.UserEntity;
import com.syy.securitytest.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public R insertUser(UserEntity user) {
        if ((user.getUserName()==null || Objects.equals(user.getUserName(), "")) || (user.getPassword()==null || Objects.equals(user.getPassword(), ""))){
            return new R(500,"用户名或密码为空!");
        }
        if (user.getUserName().length()>30){
            return new R(500,"用户名不得大于30个字符!");
        }

        int i = userMapper.insert(user);
        if (i>0){
            return new R(666,"成功");
        }else return new R(500,"失败");
    }

    @Override
    public R deleteUser(String userName) {
        if(userName==null || Objects.equals(userName, "")){
            return new R(500,"用户名为空!");
        }
        LambdaQueryWrapper<UserEntity> la = new LambdaQueryWrapper<>();
        la.eq(UserEntity::getUserName,userName);
        int i = userMapper.delete(la);
        if (i>0){
            return new R(666,"成功");
        }else return new R(500,"失败");
    }

    @Override
    public R updateUser(UserEntity user) {
        LambdaUpdateWrapper<UserEntity> la = new LambdaUpdateWrapper<>();
        la.eq(UserEntity::getUserName,user);
        int i = userMapper.update(user,la);
        if (i>0){
            return new R(666,"成功");
        }else return new R(500,"失败");
    }

    @Override
    public R selectUser(String userName) {
        LambdaQueryWrapper<UserEntity> la = new LambdaQueryWrapper<>();
        la.eq(UserEntity::getUserName,userName);
        List<UserEntity> entities = userMapper.selectList(la);
        if (entities==null || entities.size()<1){
            return new R(666,"查无此人");
        }
        return new R(666,"成功",entities);
    }

    @Override
    public R login(UserEntity entity) {
        if (entity.getUserName()==null || entity.getUserName().equals("")){
            return new R(500,"请输入用户名");
        }
        LambdaQueryWrapper<UserEntity> la = new LambdaQueryWrapper<>();
        la.eq(UserEntity::getUserName,entity.getUserName());
        List<UserEntity> entities = userMapper.selectList(la);
        if (entities==null || entities.size()<1){
            return new R(500,"用户不存在或密码不正确");
        }
        if (entities.size()>1){
            return new R(500,"系统错误");
        }
        if (entities.get(0).getPassword().equals(entity.getPassword())){
            return new R(666,"成功");
        }
        return new R(500,"用户不存在或密码不正确");
    }

    @Override
    public R register(UserEntity entity) {
        LambdaQueryWrapper<UserEntity> la = new LambdaQueryWrapper<>();
        la.eq(UserEntity::getUserName,entity.getUserName());
        if(userMapper.selectCount(la)>0){
            return new R(500,"用户已存在");
        }
        return insertUser(entity);
    }




}
