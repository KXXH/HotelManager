package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO extends OrderDTO {
    @Min(value = 1)
    private int orderRoomId;
    @Min(value = 1)
    private int roomCount;
    @Pattern(regexp = "\\d{4}-\\d\\d-\\d\\d")
    private String dateStart;
    @Pattern(regexp = "\\d{4}-\\d\\d-\\d\\d")
    private String dateEnd;
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "手机号码格式不正确")
    private String telephone;
    @NotBlank
    private String personName;
    @Min(value = 1)
    private int peopleCount;
}
