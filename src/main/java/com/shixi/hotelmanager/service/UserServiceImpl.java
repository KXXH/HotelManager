package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> selectByMap(String condition,UserMapper userMapper) {
        String key = condition.split(",")[0];
        String value = condition.split(",")[1];
        System.out.println(key+":"+value);
        Map<String,Object> map = new HashMap<>();
        map.put(key,value);
        System.out.println(userMapper);
        return userMapper.selectByMap(map);
    }

    @Override
    public boolean addUser(
            @Length(max=25,min=5) @NotBlank String username,
            @Length(max=64,min=6) @NotBlank String password,
            @Length(max=2) String gender,
            @Length(min=11,max=15) String telephone,
            @Length(max=255) @Email String email,
            @Length(min=18,max=18) @NotBlank String id_card,
            @NotBlank String avatar,
            UserMapper userMapper
    ) throws UserInfoDuplicateException {
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username)
                .or().eq("telephone",telephone)
                .or().eq("id_card",id_card)
                .or().eq("email",email);
        if(userMapper.selectCount(queryWrapper)>0){
            throw new  UserInfoDuplicateException();
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
            return true;
        }
    }
}
