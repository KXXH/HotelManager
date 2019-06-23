package com.shixi.hotelmanager.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tbl_userinfo")
public class User implements Serializable {
    @TableId("id")
    @Getter @Setter
    private int id;
    @Getter @Setter
    private String username;
    @Getter @Setter
    private String password;
}
