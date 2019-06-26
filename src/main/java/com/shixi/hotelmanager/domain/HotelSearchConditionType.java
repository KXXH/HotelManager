package com.shixi.hotelmanager.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchConditionType {

    private String high;
    private String low;
    private String like;
    private String eq;
    private String target;
    private HotelSearchConditionType and;
    private HotelSearchConditionType or;

}
