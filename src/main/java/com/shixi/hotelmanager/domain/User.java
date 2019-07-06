package com.shixi.hotelmanager.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.shixi.hotelmanager.Utils.MD5;
import com.shixi.hotelmanager.validation.UpdateTelephoneValudation;
import com.shixi.hotelmanager.validation.UpdateUserValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User extends Model<User> implements Cloneable {
    @NotBlank(groups = {UpdateUserValidation.class})
    @TableId(value="id",type= IdType.AUTO)
    private int id;

    private int userId;

    @Length(max=25,min=5,message = "用户名长度在5-25位之间")
    @NotBlank(message = "用户名不能为空")
    @Pattern(
            regexp = "^[\\u4e00-\\u9fa5a-zA-Z][\\u4e00-\\u9fa5_a-zA-Z0-9]*$",
            message = "用户名必须以英文字母或中文字符开头，且只能包含中文字符、英文字母和数字、下划线"
    )
    private String username;
    @Length(min=15,max=18,message = "身份证号必须为18位") @NotBlank(message = "身份证号不能为空") @Pattern(regexp="^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message="身份证格式错误")
    private String IdCard;
    @Length(max=8,message = "性别最多长度为8位") @Pattern(regexp = "(male)|(female)|(unknown)",message = "性别格式错误")
    private String gender;
    @Length(min=11,max=15,message = "手机号长度在11-15位之间") @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "手机号码格式不正确",groups = {Default.class, UpdateTelephoneValudation.class})
    private String telephone;
    @Length(max=255,message = "邮箱长度最大为255位") @Email(message = "必须符合邮箱格式")
    private String email;
    @Length(max=64,min=6,message = "密码长度在6-64位之间") @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "头像不能为空")
    private String avatar;
    @NotBlank(message = "角色不能为空")
    private String role;

    @Override
    protected Serializable pkVal() {
        return id;
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        Object object = super.clone();
        return object;
    }

    public void setPasswordEncode(String password){
        this.password= MD5.md5(password);
    }

}
