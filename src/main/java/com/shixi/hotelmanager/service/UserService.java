package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> selectByMap(String condition, UserMapper userMapper);
}
