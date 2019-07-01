package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_roominfo")
public class HotelRoom extends Model<HotelRoom> {
    @TableId("id")
    private int id;

    private int theRoomHotelId;

    private String bedType;

    private int count;

    private char windows;

    private char breakfast;

    private char WIFI;

    private int price;
}
