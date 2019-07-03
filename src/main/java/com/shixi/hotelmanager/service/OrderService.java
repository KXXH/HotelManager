package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.exception.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

public interface OrderService {
    @Transactional(rollbackFor = {HotelRoomInsufficientException.class})
    public boolean createOrder(CreateOrderDTO dto) throws HotelRoomInsufficientException, ParseException;
    public String payOrder(Long orderId) throws OrderNotFoundException, UserNotFoundException, OrderStatusException;
    public boolean payOrderComplete(Long orderId,String tradeNo) throws OrderNotFoundException;
    @Transactional(rollbackFor = {RefundFailException.class})
    public boolean refundOrder(Long orderId) throws RefundFailException;
}
