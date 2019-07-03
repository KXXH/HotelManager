package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.exception.RefundFailException;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {
    @Transactional
    public boolean createOrder(CreateOrderDTO dto);
    public boolean payOrder(Long orderId);
    @Transactional(rollbackFor = {RefundFailException.class})
    public boolean refundOrder(Long orderId) throws RefundFailException;
}
