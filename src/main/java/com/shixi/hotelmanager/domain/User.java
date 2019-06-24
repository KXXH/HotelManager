package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId("id")
    private int id;

    private int userId;
    private String username;
    private String IDCard;
    private String gender;
    private String telephone;
    private String email;
    private String password;
    private String avatar;
}
