package com.shixi.hotelmanager.controller;

import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shixi.hotelmanager.mapper.userMapper;
import com.shixi.hotelmanager.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String add(@RequestParam("username") String username, @RequestParam("password") String password){
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        mapper.insert(user);
        return "ok";
    }
    @GetMapping("/select/{name}")
    public List<User> select(@PathVariable String name){
        Map<String,Object> map=new HashMap<>();

        map.put("username",name);
        List<User> users = mapper.selectByMap(map);
        return users;
    }
    @GetMapping("/del/{id}")
    public Map<String,Integer> del(@PathVariable String id){
        int count=mapper.deleteById(id);
        HashMap<String,Integer> m=new HashMap<>();
        m.put("count",count);
        return m;
    }

}
