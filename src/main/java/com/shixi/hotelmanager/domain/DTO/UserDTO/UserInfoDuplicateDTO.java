package com.shixi.hotelmanager.domain.DTO.UserDTO;

import com.shixi.hotelmanager.domain.DTO.ErrorReturnDTO;
import lombok.Data;

@Data
public class UserInfoDuplicateDTO extends ErrorReturnDTO {
    public UserInfoDuplicateDTO(){
        setMsg("用户信息重复!");
        setCode(123);
    }
}
