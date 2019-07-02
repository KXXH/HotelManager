package com.shixi.hotelmanager.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_roominfo")
public class Room extends Model<Room> {
    private int id;
    private int theRoomHotelId;
    private String bedType;
    private int count;
    private char windows;
    private char breakfast;
    private char wifi;
    private int price;
}
