package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import com.shixi.hotelmanager.domain.DTO.ErrorReturnDTO;

public class CreateOrderFailDTO extends ErrorReturnDTO {
    public CreateOrderFailDTO(String msg){
        setCode(756);
        setStatus("error");
        setMsg(msg);
    }
}
