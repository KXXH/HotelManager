package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;

public interface OrderService {
    public boolean createOrder(CreateOrderDTO dto);
    public boolean payOrder(Long orderId);
    public boolean refundOrder(Long orderId);
}
