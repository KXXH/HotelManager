package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.IdType;
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
    @TableId(value="id",type= IdType.AUTO)
    private int id;

    private int userId;
    private String username;
    private String IdCard;
    private String gender;
    private String telephone;
    private String email;
    private String password;
    private String avatar;
}
