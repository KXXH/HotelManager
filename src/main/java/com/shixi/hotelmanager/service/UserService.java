package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.entity.User;
import com.shixi.hotelmanager.mapper.userMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService<T extends User> implements UserDetailsService {
    @Autowired
    private userMapper mapper;

    @Override
    public UserDetails loadUserByUsername(String username){
        try{
            HashMap<String ,Object> m=new HashMap<>();
            m.put("username",username);
            List<User> users=mapper.selectByMap(m);
            if(users.size()<=0){
                throw new UsernameNotFoundException("用户名不存在");
            }
            List<SimpleGrantedAuthority> authorities=new ArrayList<>();
            System.out.println(users.get(0).getRole());
            authorities.add(new SimpleGrantedAuthority(users.get(0).getRole().trim()));
            return new org.springframework.security.core.userdetails.User(users.get(0).getUsername(),users.get(0).getPassword(),authorities);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
