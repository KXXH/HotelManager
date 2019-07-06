package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import lombok.Data;

import java.util.List;
@Data
public class OrderAdminSearchResultDTO  extends OrderDTO{
    private List<OrderAdminReturnDTO> data;
    public OrderAdminSearchResultDTO(List<OrderAdminReturnDTO> data){
        setStatus("ok");
        setData(data);
    }
}
