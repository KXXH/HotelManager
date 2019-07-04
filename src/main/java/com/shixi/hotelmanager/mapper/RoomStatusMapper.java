package com.shixi.hotelmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shixi.hotelmanager.domain.RoomStatus;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface RoomStatusMapper extends BaseMapper<RoomStatus> {
    @Select("SELECT MAX(room_num) FROM tbl_room_status \n" +
            "WHERE room_id=${room_id} AND record_for_date>='${dateStart}' AND record_for_date<='${dateEnd}' \n" +
            "GROUP BY room_id")
    public int selectHotelRoomNum(@Param("dateStart")String dateStart, @Param("dateEnd") String dateEnd,
                                  @Param("room_id")int room_id);
}
