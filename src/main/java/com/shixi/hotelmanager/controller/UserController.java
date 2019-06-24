package com.shixi.hotelmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import com.shixi.hotelmanager.service.UserServiceImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/user")
@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;

    @GetMapping("/getAll")
    public List<User> list(){
        return userMapper.selectList(null);
    }

    @RequestMapping("/addUser")
    public Map<String,String> addUser(
            @RequestParam("username") @Length(max=25,min=5) @NotBlank String username,
            @RequestParam("password") @Length(max=64,min=6) @NotBlank String password,
            @RequestParam("gender") @Length(max=2) String gender,
            @RequestParam("telephone") @Length(min=11,max=15) String telephone,
            @RequestParam("email") @Length(max=255) @Email String email,
            @RequestParam("id_card") @Length(min=18,max=18) @NotBlank String id_card,
            @RequestParam("avatar") @NotBlank String avatar
    ){
        HashMap<String,String> m=new HashMap<>();
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username)
                .or().eq("telephone",telephone)
                .or().eq("id_card",id_card)
                .or().eq("email",email);
        if(userMapper.selectCount(queryWrapper)>0){
            m.put("status","error");
            m.put("msg","已存在用户");
            return m;
        }else{
            User user=new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setAvatar(avatar);
            user.setEmail(email);
            user.setGender(gender);
            user.setIdCard(id_card);
            user.setTelephone(telephone);
            user.setUserId(123);
            userMapper.insert(user);
            m.put("status","ok");
            return m;
        }
    }
    
}
