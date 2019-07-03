package com.shixi.hotelmanager.domain.DTO.UserDTO;


import com.shixi.hotelmanager.domain.DTO.ErrorReturnDTO;
import lombok.Data;

@Data
public class UserNotFoundDTO extends ErrorReturnDTO {
    public UserNotFoundDTO(){
        setMsg("用户未找到");
        setCode(111);
    }
}
