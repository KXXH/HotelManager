package com.shixi.hotelmanager.domain.DTO.UserDTO;

import com.shixi.hotelmanager.domain.DTO.DefaultReturnDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginPageReturnDTO extends DefaultReturnDTO {
    private String type;
}
