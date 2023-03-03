package com.syy.securitytest.user;

import com.alibaba.fastjson.JSON;
import com.syy.securitytest.dto.R;
import com.syy.securitytest.dto.UserDto;
import com.syy.securitytest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 注册接口
     * @param user
     * @return
     */
    @PostMapping("/register")
    public R insertUser(@RequestBody UserDto user){
        return userService.register(user);
    }

    /**
     * 删除用户接口
     * @param userName
     * @return
     */
    @PostMapping("/delete")
    public R deleteUser(@RequestBody String userName){
        return userService.deleteUser(userName);
    }

//    /**
//     * 查询用户信息接口
//     * @param userName
//     * @return
//     */
//    @PostMapping("/select")
//    public R selectUser(@RequestBody String userName){
//        return userService.selectUser(userName);
//    }

    /**
     * 更新用户信息接口
     * @param userEntity
     * @return
     */
    @PostMapping("/update")
    public R updateUser(@RequestBody UserDto userEntity){
        return userService.updateUser(userEntity);
    }

    /**
     * 登录接口
     * @param entity
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody UserDto entity){
        return userService.login(entity);
    }


}
