package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderDTO extends OrderDTO {
    private int orderRoomId;
    private int roomCount;
    private Date dateStart;
    private Date dateEnd;
    private String telephone;
    private String personName;
    private int peopleCount;
}
