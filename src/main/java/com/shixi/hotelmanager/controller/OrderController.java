package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchResultDTO;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/search")
    public OrderSearchResultDTO searchOrder(@RequestBody @Valid OrderSearchDTO dto, BindingResult result) throws UserNotFoundException {
        if(result.hasErrors()) throw new ValidationException(result.getAllErrors().iterator().next().toString());
        return new OrderSearchResultDTO(orderService.searchOrder(dto.getCurrentPage(),dto.getSize(),dto.getCondition()));
    }

}
