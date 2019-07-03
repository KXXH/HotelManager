package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import com.shixi.hotelmanager.domain.DTO.ErrorReturnDTO;

public class BadOrderStatusDTO extends ErrorReturnDTO {
    public BadOrderStatusDTO(){
        setCode(909);
        setMsg("订单状态不允许该操作！");
    }
}
