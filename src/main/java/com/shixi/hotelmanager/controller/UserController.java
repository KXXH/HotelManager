package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.mapper.UserMapper;
import com.shixi.hotelmanager.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/user")
@RestController
@Validated
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/getAll")
    public List<User> list(){
        return userMapper.selectList(null);
    }

    @RequestMapping("/addUser")
    public Map<String,Object> addUser(
            @Valid User user,BindingResult result
    ){
        HashMap<String,Object> m=new HashMap<>();
        if(result.hasErrors()){
            m.put("status","error");
            m.put("msg",result.getAllErrors());
            return m;
        }
        try{
            userService.addUser(user,userMapper);
        }catch(UserInfoDuplicateException e){
            m.put("status","error");
            m.put("msg","用户信息重复");
            return m;
        }
        m.put("status","ok");
        return m;
    }

    @RequestMapping("/updateUser")
    public Map<String,Object> updateUser(@Valid User user,BindingResult result){
        HashMap<String,Object> m=new HashMap<>();
        if(user.getId()==0){
            m.put("status","error");
            m.put("msg","必须传入id");
            return m;
        }

    }

}
