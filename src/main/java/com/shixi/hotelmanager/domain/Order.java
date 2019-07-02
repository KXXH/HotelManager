package com.shixi.hotelmanager.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_order")
public class Order extends Model<Order> {
    @TableId("id")
    private int id;

    private String orderId;

    private char breakfast;

    private int bedCount;

    private char windows;

    private Date dateStart;

    private Date dateEnd;

    private char cancel;

    private int roomCount;

    private int peopleCount;

    private String personName;

    private String telephone;

    private String email;

    private Date createTime;

    private String status;

    private String buyerAlipay;

    private Date orderEndTime;
}
