package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.exception.HotelRoomInsufficientException;
import com.shixi.hotelmanager.exception.OrderNotFoundException;
import com.shixi.hotelmanager.exception.OrderStatusException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

public interface OrderService {
    @Transactional(rollbackFor = {HotelRoomInsufficientException.class})
    public boolean createOrder(CreateOrderDTO dto) throws HotelRoomInsufficientException, ParseException;
    public String payOrder(Long orderId) throws OrderNotFoundException, UserNotFoundException, OrderStatusException;
    public boolean payOrderComplete(Long orderId,String tradeNo) throws OrderNotFoundException, OrderStatusException;
    @Transactional
    public boolean refundOrder(Long orderId);
    public String checkPaymentStatus(Long orderId);
}
