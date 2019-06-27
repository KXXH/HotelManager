package com.shixi.hotelmanager.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchDTO {
    private HotelSearchConditionType condition;
    private int currentPage;//1
    private int size;//999
}
