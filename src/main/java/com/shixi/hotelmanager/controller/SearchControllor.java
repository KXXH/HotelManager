package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.DTO.HotelDTO.RemainSearchDTO;
import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchControllor {

    @Autowired
    HotelService hotelService;
    @RequestMapping("/index")
    public String mapSearch(Model model){
        return "testMap";
    }

    @RequestMapping("/remain")
    public List<Hotel> remain(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){

        return hotelService.selectHotelByRemain(startDate,endDate);

    }
}
