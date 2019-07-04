package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.DTO.HotelRoomDTO.HotelRoomReturnDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelRoomService{
    List<HotelRoomReturnDTO> selectHotelRoomByRemain(String dateStart, String dateEnd, int hotel_id, String bed_type, int room_wanted);
}
