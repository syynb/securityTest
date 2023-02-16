package com.syy.securitytest.user;

import com.syy.securitytest.dto.R;
import com.syy.securitytest.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/insert")
    public R insertUser(UserEntity user){
        return userService.insertUser(user);
    }

    @PostMapping("/delete")
    public R deleteUser(String userName){
        return userService.deleteUser(userName);
    }

    @PostMapping("/select")
    public R selectUser(String userName){
        return userService.selectUser(userName);
    }

    @PostMapping("/update")
    public R updateUser(UserEntity userEntity){
        return userService.updateUser(userEntity);
    }


    @PostMapping("/login")
    public R login(UserEntity entity){
        return userService.register(entity);
    }

}
