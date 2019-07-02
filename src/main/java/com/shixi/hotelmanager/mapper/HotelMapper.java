package com.shixi.hotelmanager.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi.hotelmanager.domain.Hotel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HotelMapper extends BaseMapper<Hotel> {
@Select("SELECT DISTINCT e.*,f.remain FROM (tbl_hotelinfo e) LEFT OUTER JOIN(\n" +
        "\tSELECT \n" +
        "\tDISTINCT d.b_id,remain\n" +
        "\tFROM(\n" +
        "\t\tSELECT \n" +
        "\t\t\ta.*,\n" +
        "\t\t\tb.hotel_id as b_id,\n" +
        "\t\t\ta.total_room_capacity-MAX(b.hotel_room_ordered) as remain,\n" +
        "\t\t\tcount(*) AS c \n" +
        "\t\tFROM (\n" +
        "\t\t\tSELECT * FROM tbl_hotelinfo\n" +
        "\t\t) a \n" +
        "\t\tLEFT JOIN tbl_hotel_status b \n" +
        "\t\tON a.hotel_id=b.hotel_id \n" +
        "\t\tWHERE b.record_for_date>=DATE(\"${dateStart}\")\n" +
        "\t\t\t&&b.record_for_date<=DATE(\"${dateEnd}\")\n" +
        "\t\t\t&&b.hotel_room_ordered>=a.total_room_capacity \n" +
        "\t\tGROUP BY b.hotel_id\n" +
        "\t\t) d \n" +
        ") f ON f.b_id=e.hotel_id WHERE e.total_room_capacity>0&&f.remain IS NULL"
)
    List<Hotel> selectByRemain(Page page , @Param("dateStart")String dateStart, @Param("dateEnd") String dateEnd, @Param(Constants.WRAPPER) Wrapper wrapper);

}
