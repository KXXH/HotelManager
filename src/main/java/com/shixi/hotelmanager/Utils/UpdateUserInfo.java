package com.shixi.hotelmanager.Utils;


import com.shixi.hotelmanager.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class UpdateUserInfo {
    public static void update(User user){

        List<SimpleGrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().trim()));
        UserDetails userDetails =  new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);

        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        //1.从HttpServletRequest中获取SecurityContextImpl对象
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        //2.从SecurityContextImpl中获取Authentication对象
        Authentication authentication = securityContextImpl.getAuthentication();
        //3.初始化UsernamePasswordAuthenticationToken实例 ，这里的参数user就是我们要更新的用户信息
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials());
        auth.setDetails(authentication.getDetails());
        //4.重新设置SecurityContextImpl对象的Authentication
        securityContextImpl.setAuthentication(auth);

        System.out.println("已修改session");

    }
}
