package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.domain.UserDetail;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/change")
public class ChangePersonInfoController {

    @RequestMapping("/PersonInfo")
    public String changeInfo(Model model){
        User user = ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        model.addAttribute("User",user);
        return "changePersonInfo";
    }
}
