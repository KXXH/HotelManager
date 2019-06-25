package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> selectByMap(User user, UserMapper userMapper) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String username = user.getUsername();
        String IdCard = user.getIdCard();
        String gender = user.getGender();
        String telephone = user.getTelephone();
        String email = user.getEmail();
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
    public List<User> selectByMaps(String conditions, UserMapper userMapper){
        String[] condition = conditions.split(";");
        Map<String,Object> map = new HashMap<>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        for(String each:condition){
            String key = each.split(",")[0];
            String value = each.split(",")[1];
            queryWrapper.and(i -> i.eq(key,value));
        }
        return userMapper.selectList(queryWrapper);
    }
}
