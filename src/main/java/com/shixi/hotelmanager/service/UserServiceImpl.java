package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<User> selectByMap(Condition condition, UserMapper userMapper) {
        String key = condition.getCondition();
        String value = condition.getValue();
        //System.out.println(key+":"+value);
        Map<String,Object> map = new HashMap<>();
        map.put(key,value);
        //System.out.println(userMapper);
        return userMapper.selectByMap(map);
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
