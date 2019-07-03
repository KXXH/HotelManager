package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefoundDTO extends OrderDTO {
    private long orderId;
}
