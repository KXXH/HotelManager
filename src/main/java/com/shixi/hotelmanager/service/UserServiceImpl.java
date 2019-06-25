package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.mapper.UserMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> selectByMap(Condition condition, UserMapper userMapper) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String username = condition.getUsername();
        String IdCard = condition.getIdCard();
        String gender = condition.getGender();
        String telephone = condition.getTelephone();
        String email = condition.getEmail();
        queryWrapper.like("email","@");

        if(StringUtils.isNotBlank(username))
            queryWrapper.and(wrapper -> wrapper.like("username",username));
        if(StringUtils.isNotBlank(IdCard))
            queryWrapper.and(wrapper -> wrapper.like("id_card",IdCard));
        if(StringUtils.isNotBlank(gender))
            queryWrapper.and(wrapper -> wrapper.eq("gender",gender));
        if(StringUtils.isNotBlank(telephone))
            queryWrapper.and(wrapper -> wrapper.like("telephone",telephone));
        if(email.length()>1)
            queryWrapper.and(wrapper -> wrapper.like("email",email));


        //System.out.println(userMapper);
        return userMapper.selectList(queryWrapper);
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
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username)
                .or().eq("telephone", telephone)
                .or().eq("id_card", id_card)
                .or().eq("email", email);
        if (userMapper.selectCount(queryWrapper) > 0) {
            throw new UserInfoDuplicateException();
        } else {
            User user = new User();
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

    @Override
    public boolean addUser(User user,UserMapper userMapper) throws UserInfoDuplicateException {
        return addUser(user.getUsername(),user.getPassword(),user.getGender(),user.getTelephone(),user.getEmail(),user.getIdCard(),user.getAvatar(),userMapper);
    }

    @Override
    public boolean updateUser(User user, UserMapper userMapper) throws UserNotFoundException {
        int count=userMapper.updateById(user);
        if(count==0){
            throw new UserNotFoundException();
        }
        else{
            return true;
        }
    }
    public boolean deleteByid(int id,UserMapper userMapper) throws UserNotFoundException {
        User user = userMapper.selectById(id);
        if (user == null){
            throw new UserNotFoundException();
        }
        int result = userMapper.deleteById(id);
        if (result == 1)
            return true;
        else
            return false;
    }
}
