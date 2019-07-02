package com.shixi.hotelmanager.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ID_WORKER;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_order")
public class Order extends Model<Order> {
    @TableId(type=ID_WORKER)
    private long id;

    private int orderUserId;

    private String orderId;

    private int roomCount;

    private Date dateStart;

    private Date dateEnd;

    private double price;

    private String status;

    private int orderRoomId;

    private String telephone;
    private String personName;
    private int peopleCount;

}
