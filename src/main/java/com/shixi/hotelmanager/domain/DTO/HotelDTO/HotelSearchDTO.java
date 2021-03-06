package com.shixi.hotelmanager.domain.DTO.HotelDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchDTO {
    private HotelSearchConditionType condition;
    private int currentPage=1;//1
    private int size=20;//999
}
