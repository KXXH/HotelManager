package com.shixi.hotelmanager.domain.DTO.HotelDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchDTO {
    private HotelSearchConditionType condition;
    private int currentPage;
    private int size;
}
