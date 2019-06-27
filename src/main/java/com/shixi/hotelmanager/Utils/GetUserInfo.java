package com.shixi.hotelmanager.Utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.mapper.UserMapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetUserInfo {

    public static User getInfo(UserMapper userMapper){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            System.out.println(currentUserName);
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("username",currentUserName);
            User user = userMapper.selectOne(queryWrapper);
            return user;
        }
        return null;
    }
}
