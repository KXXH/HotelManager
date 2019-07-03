package com.shixi.hotelmanager.controller;

import com.alipay.api.AlipayApiException;
import com.shixi.hotelmanager.domain.DTO.SearchDTO;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.service.OrderManagerService;
import com.shixi.hotelmanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/refund")
    public boolean helpUserRefund(Order order) throws AlipayApiException {
        return orderService.makeFundOrder(order);
    }
}
