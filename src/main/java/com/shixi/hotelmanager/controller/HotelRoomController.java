package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.HotelRoom;
import com.shixi.hotelmanager.service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/admin/hotelRoom")
public class HotelRoomController {
    @Autowired
    HotelRoomService hotelRoomService;

    @RequestMapping("/remain")
    public List<HotelRoom> remain(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate,
                                  @RequestParam("hotel_id") int hotel_id, @RequestParam("bed_type") String bed_type,
                                  @RequestParam("room_wanted")int room_wanted){

        return hotelRoomService.selectHotelRoomByRemain(startDate,endDate,hotel_id,bed_type,room_wanted);

    }
}
