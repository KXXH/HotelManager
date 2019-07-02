package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.DTO.HotelRoomDTO.HotelRoomSearchDTO;
import com.shixi.hotelmanager.domain.HotelRoom;
import com.shixi.hotelmanager.service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/admin/hotelRoom")
public class HotelRoomController {
    @Autowired
    HotelRoomService hotelRoomService;

    @RequestMapping("/remain")
    public List<HotelRoom> remain(@RequestBody HotelRoomSearchDTO hotelRoomSearchDTO){

        return hotelRoomService.selectHotelRoomByRemain(hotelRoomSearchDTO.getStartDate()
                ,hotelRoomSearchDTO.getEndDate(),hotelRoomSearchDTO.getHotelId(),
                hotelRoomSearchDTO.getBedType(),hotelRoomSearchDTO.getRoomWanted());
    }
}
