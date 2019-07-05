package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.domain.UserDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/change")
public class ChangePersonInfoController {

    @RequestMapping("/PersonInfo")
    @ResponseBody
    public User changeInfo(){
        User user = ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        return user;
    }
}
