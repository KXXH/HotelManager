package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchConditionType {
    private String like;
    private String eq;
    private String orderByDesc;
    private String orderByAsc;
    private String high;
    private String low;
    private List<String> in;
    private String target;
    private OrderSearchConditionType and;
    private OrderSearchConditionType or;
}
