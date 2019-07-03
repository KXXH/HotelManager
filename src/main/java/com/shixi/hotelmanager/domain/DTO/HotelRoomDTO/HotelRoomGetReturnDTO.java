package com.shixi.hotelmanager.domain.DTO.HotelRoomDTO;

import com.shixi.hotelmanager.domain.DTO.DefaultReturnDTO;
import com.shixi.hotelmanager.domain.HotelRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoomGetReturnDTO extends DefaultReturnDTO {
    private List<HotelRoom> rooms;
}
