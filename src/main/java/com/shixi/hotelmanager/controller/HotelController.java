package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.mapper.HotelMapper;
import com.shixi.hotelmanager.service.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelMapper hotelMapper;

    @Autowired
    HotelServiceImpl hotelService;

    @RequestMapping("/get")
    public Map<String,Object> get(@RequestParam(value = "current_page",defaultValue = "1") String currentPage,@RequestParam(value = "page_size",defaultValue = "20") String pageSize){
        HashMap<String,Object> m=new HashMap<>();
        m.put("status","ok");
        m.put("data",hotelService.selectByPage(Integer.parseInt(currentPage),Integer.parseInt(pageSize),hotelMapper));
        return m;
    }
}
