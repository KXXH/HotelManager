package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.ChangePasswdDTO;
import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

public interface UserService <T extends User> extends UserDetailsService {
    boolean addUser(
            String username,
            String password,
            String gender,
            String telephone,
            String email,
            String id_card,
            String avatar
    ) throws UserInfoDuplicateException;
    boolean addUser(User user) throws UserInfoDuplicateException;
    boolean updateUser(User user) throws UserNotFoundException, UserInfoDuplicateException;
    List<User> selectByMap(Condition condition);
    boolean deleteByid(int id) throws UserNotFoundException;
    int deleteByids(ArrayList ids);
    int changePasswd(ChangePasswdDTO changePasswdDTO);
}
