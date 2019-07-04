package com.shixi.hotelmanager.domain.DTO.HotelRoomDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoomSearchDTO {
    @Pattern(regexp = "dddd-dd-dd", message="传入的日期必须是yyyy-MM-dd形式，含前导0")
    private String startDate;
    @Pattern(regexp = "dddd-dd-dd", message="传入的日期必须是yyyy-MM-dd形式，含前导0")
    private String endDate;
    private int hotelId;
    private String bedType;
    private int roomWanted;
}
