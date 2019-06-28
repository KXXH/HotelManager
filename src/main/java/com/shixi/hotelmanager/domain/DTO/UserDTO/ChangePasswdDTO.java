package com.shixi.hotelmanager.domain.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswdDTO {
    @Length(max=64,min=6,message = "密码长度在6-64位之间") @NotBlank(message = "密码不能为空")
    private String OldPassword;
    @Length(max=64,min=6,message = "密码长度在6-64位之间") @NotBlank(message = "密码不能为空")
    private String NewPassword;
    @Length(max=64,min=6,message = "密码长度在6-64位之间") @NotBlank(message = "密码不能为空")
    private String Confirmation;
}
