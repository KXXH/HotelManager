package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_order")
public class Order extends Model<Order> {
    private int id;

    private String orderId;

    private double price;

    private String status;

    private int orderRoomId;

}
