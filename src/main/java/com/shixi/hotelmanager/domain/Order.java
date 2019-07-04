package com.shixi.hotelmanager.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

import static com.baomidou.mybatisplus.annotation.IdType.ID_WORKER;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_order")
public class Order extends Model<Order> {
    @TableId(type=ID_WORKER)
    private long id;

    @Pattern(regexp = "\\d+")
    private int orderUserId;

    private String orderId;

    @Pattern(regexp = "\\d+")
    private int roomCount;

    @Pattern(regexp = "\\d{4}-\\d\\d-\\d\\d")
    private String dateStart;

    @Pattern(regexp = "\\d{4}-\\d\\d-\\d\\d")
    private String dateEnd;

    @Min(value = 0)
    @NotBlank
    private double price;


    private String status;

    @Pattern(regexp = "\\d+")
    private int orderRoomId;
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "手机号码格式不正确")
    private String telephone;
    @NotBlank
    private String personName;
    @Min(value = 1)
    private int peopleCount;

    private char breakfast;
    private char windows;
    private String hotelName;
    private int hotelId;
    private String uuid;
    private Date createTime;
    private String buyerAlipay;
}
