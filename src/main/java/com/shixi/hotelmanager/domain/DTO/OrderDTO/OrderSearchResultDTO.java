package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import com.shixi.hotelmanager.domain.Order;

import java.util.List;

public class OrderSearchResultDTO extends OrderDTO {
    private List<Order> data;
    public OrderSearchResultDTO(List<Order> data){
        setStatus("ok");
        this.data=data;
    }
}
