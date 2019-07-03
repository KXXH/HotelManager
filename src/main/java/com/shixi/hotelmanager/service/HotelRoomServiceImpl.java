package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi.hotelmanager.domain.HotelRoom;
import com.shixi.hotelmanager.mapper.HotelRoomMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value="hotelRoomService")
public class HotelRoomServiceImpl extends ServiceImpl<HotelRoomMapper, HotelRoom> implements HotelRoomService {
    @Override
    public List<HotelRoom> selectHotelRoomByRemain(String dateStart, String dateEnd, int hotel_id, String bed_type,int room_wanted) {

        return baseMapper.selectHotelRoomByRemain(dateStart,dateEnd,hotel_id,bed_type,room_wanted);
    }

}
