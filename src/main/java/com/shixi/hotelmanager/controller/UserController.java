package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import com.shixi.hotelmanager.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserServiceImpl userService = new UserServiceImpl();

    @GetMapping("/get")
    public List<User> list(){
        return userMapper.selectList(null);
    }

    @RequestMapping(value = "simple_select")
    public List<User> selectByCondition(@Valid User user){
        List<User> users = userService.selectByMap(user,userMapper);

        return users;
    }


    @RequestMapping("/complex_select/{conditions}")
    public List<User> selectByConditions(@PathVariable String conditions){
        List<User> users = userService.selectByMaps(conditions,userMapper);
        return users;
    }

}
