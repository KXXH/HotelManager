package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.mapper.HotelMapper;

import java.util.List;

public interface HotelService {
    List<Hotel> selectByPage(int page, int size, HotelMapper hotelMapper);
}
