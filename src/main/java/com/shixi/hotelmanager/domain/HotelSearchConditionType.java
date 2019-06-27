package com.shixi.hotelmanager.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchConditionType {

    private String high;
    private String low;
    private String like;
    private String eq;
    private List<String> in;
    private String target;
    private String orderByAsc;
    private String orderByDesc;
    private HotelSearchConditionType and;
    private HotelSearchConditionType or;

}
