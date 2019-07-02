package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_hotel_status")
public class HotelStatus extends Model<HotelStatus> {
    private int id;
    private int hotelId;
    private int hotelRoomOrdered;
    private Date recordForDate;
    @Version
    private int version;
}
