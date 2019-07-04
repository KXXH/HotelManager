package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class OrderSearchResultDTO extends OrderDTO {
    private List<OrderReturnDTO> data;
    public OrderSearchResultDTO(List<OrderReturnDTO> data){
        setStatus("ok");
        setData(data);
    }
}
