package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.shixi.hotelmanager.domain.DTO.HotelDTO.HotelSearchConditionType;
import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.exception.HotelInfoDuplicateException;
import com.shixi.hotelmanager.exception.HotelNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HotelService {
    List<Hotel> selectByPage(int page, int size);
    boolean addHotel(Hotel hotel) throws HotelInfoDuplicateException;
    boolean updateHotel(Hotel hotel) throws HotelNotFoundException,HotelInfoDuplicateException;
    List<Hotel> searchHotel(int current, int size, HotelSearchConditionType conditionType);
    List<Hotel> selectHotelByRemain(String dateStart, String dateEnd);
    List<Hotel> selectHotelByRemain(String dateStart, String dateEnd, Wrapper wrapper);
    List<Hotel> selectHotelByRemain(String dateStart, String dateEnd, HotelSearchConditionType conditionType);
    int delHotel(List<Integer> delIds);
}
