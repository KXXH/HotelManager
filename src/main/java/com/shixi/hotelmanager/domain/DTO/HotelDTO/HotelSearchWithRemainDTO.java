package com.shixi.hotelmanager.domain.DTO.HotelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelSearchWithRemainDTO extends HotelSearchDTO {
    @Pattern(regexp = "dddd-dd-dd", message="传入的日期必须是yyyy-MM-dd形式，含前导0")
    private String startDate;
    @Pattern(regexp = "dddd-dd-dd", message="传入的日期必须是yyyy-MM-dd形式，含前导0")
    private String endDate;
}
