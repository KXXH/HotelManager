package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import com.shixi.hotelmanager.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("/get")
    public List<User> list(){
        return userMapper.selectList(null);
    }

    @RequestMapping("/select/{condition}")
    public List<User> selectByCondituin(@PathVariable String condition){
        UserServiceImpl userService = new UserServiceImpl();
        List<User> users = userService.selectByMap(condition,userMapper);
        return users;
    }
}
