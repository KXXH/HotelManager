package com.shixi.hotelmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi.hotelmanager.domain.HotelRoom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelRoomMapper extends BaseMapper<HotelRoom> {
    @Select("SELECT tbl_roominfo.* FROM tbl_roominfo,\n" +
            "(SELECT MAX(room_num) AS count FROM\n" +
            "(SELECT id FROM tbl_roominfo WHERE the_room_hotel_id=${hotel_id} AND bed_type='${bed_type}') d \n" +
            "LEFT JOIN tbl_room_status ON d.id=tbl_room_status.room_id\n" +
            "WHERE record_for_date>='${dateStart}' AND record_for_date<='${dateEnd}') e \n" +
            "WHERE tbl_roominfo.count>=e.count+${room_wanted} AND tbl_roominfo.the_room_hotel_id=${hotel_id} AND bed_type='${bed_type}'")
    List<HotelRoom> selectHotelRoomByRemain(@Param("dateStart")String dateStart, @Param("dateEnd") String dateEnd,
                                   @Param("hotel_id")int hotel_id,@Param("bed_type")String bed_type,
                                   @Param("room_wanted")int room_wanted);

}

