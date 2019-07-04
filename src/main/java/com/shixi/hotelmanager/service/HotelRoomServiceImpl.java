package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi.hotelmanager.domain.DTO.HotelRoomDTO.HotelRoomReturnDTO;
import com.shixi.hotelmanager.domain.HotelRoom;
import com.shixi.hotelmanager.mapper.HotelRoomMapper;
import com.shixi.hotelmanager.mapper.RoomStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value="hotelRoomService")
public class HotelRoomServiceImpl extends ServiceImpl<HotelRoomMapper, HotelRoom> implements HotelRoomService {

    @Autowired
    private RoomStatusMapper roomStatusMapper;

    @Override
    public List<HotelRoomReturnDTO> selectHotelRoomByRemain(String dateStart, String dateEnd, int hotel_id, String bed_type, int room_wanted) {
        List<HotelRoomReturnDTO> returnList = new ArrayList<>();
        HotelRoomReturnDTO hotelRoomReturnDTO = new HotelRoomReturnDTO();
        List<HotelRoom> list = baseMapper.selectHotelRoomByRemain(dateStart,dateEnd,hotel_id,bed_type,room_wanted);
        for(HotelRoom each:list){
            int theRoomId = each.getId();
            int num = roomStatusMapper.selectHotelRoomNum(dateStart,dateEnd,theRoomId);
            System.out.println(num);
            hotelRoomReturnDTO.setHotelRoom(each);
            hotelRoomReturnDTO.setNum(each.getCount()-num);
            returnList.add(hotelRoomReturnDTO);
        }
        return returnList;
    }

}
