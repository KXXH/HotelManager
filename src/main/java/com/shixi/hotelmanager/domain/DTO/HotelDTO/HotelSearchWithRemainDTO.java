package com.shixi.hotelmanager.domain.DTO.HotelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchWithRemainDTO extends HotelSearchDTO {
private String startDate;
private String endDate;
}
