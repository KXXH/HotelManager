package com.shixi.hotelmanager.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shixi.hotelmanager.domain.Hotel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelMapper extends BaseMapper<Hotel> {
@Select("SELECT DISTINCT d.* FROM(" +
        "SELECT " +
        "a.*," +
        "b.hotel_id as b_id," +
        "a.total_room_capacity-MAX(b.hotel_room_ordered) as remain," +
        "count(*) AS c " +
        "FROM (" +
        "SELECT * FROM tbl_hotelinfo ${ew.customSqlSegment}" +
        ") a " +
        "LEFT JOIN tbl_hotel_status b " +
        "ON a.hotel_id=b.hotel_id " +
        "WHERE b.record_for_date>=DATE(\"${dateStart}\")" +
        "&&b.record_for_date<=DATE(\"${dateEnd}\")" +
        "&&b.hotel_room_ordered<a.total_room_capacity  " +
        "GROUP BY b.hotel_id" +
        ") d " +
        "WHERE d.c>DATEDIFF(\"${dateEnd}\",\"${dateStart}\")")
    List<Hotel> selectByRemain(@Param("dateStart")String dateStart, @Param("dateEnd") String dateEnd,@Param(Constants.WRAPPER) Wrapper wrapper);

}
