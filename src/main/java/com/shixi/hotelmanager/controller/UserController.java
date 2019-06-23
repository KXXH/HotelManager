package com.shixi.hotelmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.entity.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserMapper mapper;

    @RequestMapping("/{name}")
    public User getUser(@PathVariable String name){
        return mapper.selectOne(new QueryWrapper<User>().eq("id","1").eq("name","fcc"));
    }
}
