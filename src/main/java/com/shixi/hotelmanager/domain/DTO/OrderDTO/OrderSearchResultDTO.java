package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import com.shixi.hotelmanager.domain.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderSearchResultDTO extends OrderDTO {
    private List<Order> data;
    public OrderSearchResultDTO(List<Order> data){
        setStatus("ok");
        setData(data);
    }
}
