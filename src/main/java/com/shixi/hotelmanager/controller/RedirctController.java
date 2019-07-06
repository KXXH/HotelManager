package com.shixi.hotelmanager.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.Set;

@Controller
@RequestMapping("/")
public class RedirctController {

    @RequestMapping("/")
    public String updateOrAddProject() {
        ServletRequestAttributes attr = (ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes();
        HttpSession session= attr.getRequest().getSession(true);
        //1.从HttpServletRequest中获取SecurityContextImpl对象
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) session.getAttribute("SPRING_SECURITY_CONTEXT");
        Authentication authentication = securityContextImpl.getAuthentication();
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println(roles);
        if (roles.contains("ROLE_ADMIN"))
            return "redirect:/public/test1/index.html";
        else
            return "redirect:/public/checkout/index.html";
    }
}
