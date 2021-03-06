package com.shixi.hotelmanager.controller;

import com.alipay.api.AlipayApiException;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.*;
import com.shixi.hotelmanager.domain.DTO.SearchDTO;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.exception.OrderNotFoundException;
import com.shixi.hotelmanager.exception.OutdatedOrdersException;
import com.shixi.hotelmanager.exception.RefundFailException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.service.OrderManagerService;
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
@RequestMapping("/admin/order")
public class OrderManagerController {

    @Autowired
    private OrderManagerService orderManagerService;

    @Autowired
    private OrderService orderService;

    @RequestMapping("/getAllOrder")
    public List<Order> getAllOrder(SearchDTO searchDTO){
        return orderManagerService.getAllOrder(searchDTO.getCurrent(),searchDTO.getSize());
    }

    @RequestMapping("/search")
    public OrderAdminSearchResultDTO search(@RequestBody @Valid OrderSearchDTO dto, BindingResult result) throws UserNotFoundException {
        if(result.hasErrors()) throw new ValidationException(result.getAllErrors().iterator().next().toString());
        List<OrderAdminReturnDTO> returnDTOS=new ArrayList<>();
        List<Order> searchResult=orderService.searchOrder(dto.getCurrentPage(),dto.getSize(),dto.getCondition(),true);
        for(Order order:searchResult){
            returnDTOS.add(new OrderAdminReturnDTO(order));
        }
        return new OrderAdminSearchResultDTO(returnDTOS);
    }

    @RequestMapping("/refund")
    public boolean helpUserRefund(Order order) throws AlipayApiException {
        try {
            return orderService.makeFundOrder(order);
        } catch (OrderNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (RefundFailException e) {
            e.printStackTrace();
            return false;
        } catch (OutdatedOrdersException e) {
            e.printStackTrace();
            return false;
        }
    }
}
