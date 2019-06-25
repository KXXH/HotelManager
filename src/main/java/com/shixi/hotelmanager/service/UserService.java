package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.mapper.UserMapper;

import java.util.List;

public interface UserService {
    List<User> selectByMap(String condition, UserMapper userMapper);
    boolean addUser(
            String username,
            String password,
            String gender,
            String telephone,
            String email,
            String id_card,
            String avatar,
            UserMapper userMapper
    ) throws UserInfoDuplicateException;
    boolean addUser(User user,UserMapper userMapper) throws UserInfoDuplicateException;
    boolean updateUser(User user,UserMapper userMapper) throws UserNotFoundException, UserInfoDuplicateException;
    List<User> selectByMap(Condition condition, UserMapper userMapper);
    boolean deleteByid(int id,UserMapper userMapper) throws UserNotFoundException;
}
