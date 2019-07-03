package com.shixi.hotelmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi.hotelmanager.domain.HotelRoom;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelRoomMapper extends BaseMapper<HotelRoom> {
    @Select("SELECT * FROM tbl_roominfo WHERE bed_type='${bed_type}'&&the_room_hotel_id=${hotel_id}&&id NOT IN(" +
            "SELECT tbl_roominfo.id AS id FROM(" +
            "SELECT id,COUNT(*) AS used FROM(" +
            "SELECT " +
            "DISTINCT room_num," +
            "b.room_id AS id " +
            "FROM tbl_roominfo a " +
            "LEFT JOIN tbl_room_status b " +
            "ON a.id=b.room_id " +
            "WHERE a.the_room_hotel_id=${hotel_id}" +
            "&&a.bed_type='${bed_type}'" +
            "&&b.record_for_date>=DATE(\"${dateStart}\")" +
            "&&b.record_for_date<=DATE(\"${dateEnd}\")" +
            ") d " +
            "GROUP BY id" +
            ") e LEFT JOIN tbl_roominfo ON e.id=tbl_roominfo.id " +
            "WHERE used > count-${room_wanted}" +
            ")")
    List<HotelRoom> selectHotelRoomByRemain(@Param("dateStart")String dateStart, @Param("dateEnd") String dateEnd,
                                   @Param("hotel_id")int hotel_id,@Param("bed_type")String bed_type,
                                   @Param("room_wanted")int room_wanted);

}
