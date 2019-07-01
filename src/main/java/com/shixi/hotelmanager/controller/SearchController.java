package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    HotelService hotelService;
    @RequestMapping("/index")
    public String mapSearch(Model model){
        return "testMap";
    }

}
