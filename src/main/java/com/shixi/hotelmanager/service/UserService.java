package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.mapper.UserMapper;

import java.util.List;

public interface UserService {
    List<User> selectByMap(Condition condition, UserMapper userMapper);
    boolean deleteByid(int id) throws UserNotFoundException;
}
