package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
