package com.shixi.hotelmanager.domain.DTO.UserDTO;

import com.shixi.hotelmanager.validation.UpdateTelephoneValudation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForgetPasswordDTO {
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "手机号码格式不正确")
    private String telephone;

    private int code;

    @Length(max=64,min=6,message = "密码长度在6-64位之间") @NotBlank(message = "密码不能为空")
    private String newPassword;
}
