package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
}
