package com.shixi.hotelmanager.Utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GetUserInfo {

    public static User getInfo(UserMapper userMapper){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String currentUserName = ((UserDetails) principal).getUsername();
            System.out.println("==================================");
            System.out.println(principal);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",currentUserName);
            User user = userMapper.selectOne(queryWrapper);
            return user;
        }
        else if(principal instanceof User)
            return (User) principal;
        return null;
    }
}
