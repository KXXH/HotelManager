package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchDTO extends OrderDTO{
private int currentPage;
private int size;
private OrderSearchConditionType condition;
}
