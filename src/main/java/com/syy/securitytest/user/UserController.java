package com.syy.securitytest.user;

import com.alibaba.fastjson.JSON;
import com.syy.securitytest.dto.R;
import com.syy.securitytest.user.service.UserService;
import com.syy.securitytest.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public R insertUser(@RequestBody UserEntity user){
        return userService.register(user);
    }

    @PostMapping("/delete")
    public R deleteUser(@RequestBody String userName){
        return userService.deleteUser(userName);
    }

    @PostMapping("/select")
    public R selectUser(@RequestBody String userName){
        return userService.selectUser(userName);
    }

    @PostMapping("/update")
    public R updateUser(@RequestBody UserEntity userEntity){
        return userService.updateUser(userEntity);
    }


    @PostMapping("/login")
    public R login(@RequestBody UserEntity entity){
        return userService.login(entity);
    }




}
