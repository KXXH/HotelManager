package com.shixi.hotelmanager.domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorReturnDTO extends DefaultReturnDTO {
    private String msg;
    private int code;
}
