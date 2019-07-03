package com.shixi.hotelmanager.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.DTO.HotelRoomDTO.HotelRoomGetReturnDTO;
import com.shixi.hotelmanager.domain.DTO.HotelRoomDTO.HotelRoomSearchDTO;
import com.shixi.hotelmanager.domain.HotelRoom;
import com.shixi.hotelmanager.service.HotelRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/hotelRoom")
public class HotelRoomController {
    @Autowired
    HotelRoomService hotelRoomService;

    @RequestMapping("/remain")
    public List<HotelRoom> remain(@RequestBody HotelRoomSearchDTO hotelRoomSearchDTO){

        return hotelRoomService.selectHotelRoomByRemain(hotelRoomSearchDTO.getStartDate()
                ,hotelRoomSearchDTO.getEndDate(),hotelRoomSearchDTO.getHotelId(),
                hotelRoomSearchDTO.getBedType(),hotelRoomSearchDTO.getRoomWanted());
    }

    @RequestMapping("/getRooms")
    public HotelRoomGetReturnDTO getRooms(HotelRoomSearchDTO dto){
        QueryWrapper<HotelRoom> hotelRoomQueryWrapper=new QueryWrapper<>();
        hotelRoomQueryWrapper.eq("the_room_hotel_id",dto.getHotelId());
        HotelRoomGetReturnDTO returnDTO=new HotelRoomGetReturnDTO();
        HotelRoom hotelRoom=new HotelRoom();
        returnDTO.setRooms(hotelRoom.selectList((Wrapper<HotelRoom>) hotelRoomQueryWrapper));
        return returnDTO;
    }
}
