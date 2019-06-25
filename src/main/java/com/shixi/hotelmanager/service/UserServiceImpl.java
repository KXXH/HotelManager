package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.mapper.UserMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.stereotype.Service;

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
    public boolean deleteByid(int id) throws UserNotFoundException {

    }

    @Override
    public boolean addUser(User user,UserMapper userMapper) throws UserInfoDuplicateException {
        return addUser(user.getUsername(),user.getPassword(),user.getGender(),user.getTelephone(),user.getEmail(),user.getIdCard(),user.getAvatar(),userMapper);
    }
}
