package com.shixi.hotelmanager.domain.DTO.HotelRoomDTO;

import com.shixi.hotelmanager.domain.HotelRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoomReturnDTO {
    private HotelRoom hotelRoom;
    private int num;
}
