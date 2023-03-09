package com.syy.securitytest.keymanager.controller;

import com.syy.securitytest.dto.R;
import com.syy.securitytest.dto.UserDto;
import com.syy.securitytest.keymanager.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/key")
@CrossOrigin
public class KeyController {

    @Autowired
    KeyService service;

    @PostMapping("/getKey")
    public String getKey(String token, HttpServletRequest request){
        return service.sendKey(token,request);
    }

    @PostMapping("/getToken")
    public R getToken(UserDto dto, HttpServletRequest request){
        return service.getToken(dto,request);
    }

    @PostMapping("/generateKey")
    public R generateKey(UserDto dto){
        return service.generateKey(dto);
    }

    @PostMapping("/updateKey")
    public R updateKey(UserDto dto){
        return service.updateKey(dto);
    }

    @PostMapping("/deleteKey")
    public R deleteKey(UserDto dto){
        return service.deleteKey(dto);
    }

    @PostMapping("/encrypt")
    public R encrypt(UserDto dto,String message){
        return service.encrypt(dto,message);
    }
}
