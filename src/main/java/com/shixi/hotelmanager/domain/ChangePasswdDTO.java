package com.shixi.hotelmanager.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswdDTO {
    private String OldPassword;
    private String NewPassword;
    private String Confirmation;
}
