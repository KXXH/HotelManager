package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User {
    @TableId(value="id",type= IdType.AUTO)
    private int id;

    private int userId;
    @Length(max=25,min=5,message = "用户名长度在5-25位之间") @NotBlank(message = "用户名不能为空")
    private String username;
    @Length(min=18,max=18,message = "身份证号必须为18位") @NotBlank(message = "身份证号不能为空")
    private String IdCard;
    @Length(max=2,message = "性别最多长度为2位")
    private String gender;
    @Length(min=11,max=15,message = "手机号长度在11-15位之间")
    private String telephone;
    @Length(max=255,message = "邮箱长度最大为255位") @Email(message = "必须符合邮箱格式")
    private String email;
    @Length(max=64,min=6,message = "密码长度在6-64位之间") @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "头像不能为空")
    private String avatar;
}
