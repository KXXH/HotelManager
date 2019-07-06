package com.shixi.hotelmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirctController {

    @RequestMapping("/")
    public String updateOrAddProject() {
        return "redirect:/public/checkout/index.html";
    }
}
