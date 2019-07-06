package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.domain.User;
import lombok.Data;

@Data

public class OrderAdminReturnDTO extends OrderReturnDTO {
    private String username;
    private int userId;
    public OrderAdminReturnDTO(Order order){
        super(order);
        User user=new User();
        user=user.selectById(order.getOrderUserId());
        if(user!=null){
            setUserId(user.getId());
            setUsername(user.getUsername());
        }
    }
}
