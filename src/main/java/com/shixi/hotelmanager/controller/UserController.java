package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.mapper.userMapper;
import com.shixi.hotelmanager.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private userMapper mapper;
    @GetMapping("/get")
    public List<com.shixi.hotelmanager.entity.User> list(){
        return mapper.selectList(null);
    }
    @GetMapping("/add")
    public String add(){
        User user=new User();
        user.setUsername("asd");
        user.setPassword("ert");
        mapper.insert(user);
        return "ok";
    }

}
