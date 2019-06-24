package com.shixi.hotelmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserServiceException;
import com.shixi.hotelmanager.mapper.UserMapper;
import com.shixi.hotelmanager.service.UserService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import com.shixi.hotelmanager.service.UserServiceImpl;
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
    public Map<String,String> addUser(
            @RequestParam("username") @Length(max=25,min=5,message = "用户名长度在5-25位之间") @NotBlank(message = "用户名不能为空") String username,
            @RequestParam("password") @Length(max=64,min=6,message = "密码长度在6-64位之间") @NotBlank(message = "密码不能为空") String password,
            @RequestParam("gender") @Length(max=2,message = "性别最多长度为2位") String gender,
            @RequestParam("telephone") @Length(min=11,max=15,message = "手机号长度在11-15位之间") String telephone,
            @RequestParam("email") @Length(max=255,message = "邮箱长度最大为255位") @Email(message = "必须符合邮箱格式") String email,
            @RequestParam("id_card") @Length(min=18,max=18,message = "身份证号必须为18位") @NotBlank(message = "身份证号不能为空") String id_card,
            @RequestParam("avatar") @NotBlank(message = "头像不能为空") String avatar
    ){
        HashMap<String,String> m=new HashMap<>();
        try{
            userService.addUser(username,password,gender,telephone,email,id_card,avatar,userMapper);
        }catch(UserInfoDuplicateException e){
            m.put("status","error");
            m.put("msg","用户信息重复");
            e.printStackTrace();
            return m;
        }
        m.put("status","ok");
        return m;
    }
}
