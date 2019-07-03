package com.shixi.hotelmanager.domain.DTO.HotelRoomDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoomSearchDTO {
    private String startDate;
    private String endDate;
    private int hotelId;
    private String bedType;
    private int roomWanted;
}
