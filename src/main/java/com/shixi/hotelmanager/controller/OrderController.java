package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderReturnDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchResultDTO;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("/search")
    public OrderSearchResultDTO searchOrder(@RequestBody @Valid OrderSearchDTO dto, BindingResult result) throws UserNotFoundException {
        if(result.hasErrors()) throw new ValidationException(result.getAllErrors().iterator().next().toString());
        List<OrderReturnDTO> returnDTOS=new ArrayList<>();
        List<Order> searchResult=orderService.searchOrder(dto.getCurrentPage(),dto.getSize(),dto.getCondition());
        for(Order order:searchResult){
            returnDTOS.add(new OrderReturnDTO(order));
        }
        return new OrderSearchResultDTO(returnDTOS);
    }

}
