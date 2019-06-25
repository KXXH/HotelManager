package com.shixi.hotelmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Condition {
    @Length(max=25,message = "用户名长度在5-25位之间")
    private String username;
    @Length(max=18,message = "身份证号必须为18位")
    private String IdCard;
    @Length(max = 8,message = "性别最多长度为8位")
    private String gender;
    @Length(max=15,message = "手机号长度在11-15位之间")
    private String telephone;
    @Length(max=255,message = "邮箱长度最大为255位")
    private String email;
}
