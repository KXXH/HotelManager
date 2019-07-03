package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchResultDTO;
import com.shixi.hotelmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/search")
    public OrderSearchResultDTO searchOrder(@RequestBody OrderSearchDTO dto){
        return new OrderSearchResultDTO(orderService.searchOrder(dto.getCurrentPage(),dto.getSize(),dto.getCondition()));
    }

}
