package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.exception.HotelRoomInsufficientException;
import com.shixi.hotelmanager.exception.OrderNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import com.shixi.hotelmanager.exception.RefundFailException;
import org.springframework.transaction.annotation.Transactional;
public interface OrderService {
    @Transactional(rollbackFor = {HotelRoomInsufficientException.class})
    public boolean createOrder(CreateOrderDTO dto) throws HotelRoomInsufficientException;
    public String payOrder(Long orderId) throws OrderNotFoundException;
    public boolean payOrderComplete(Long orderId,String tradeNo) throws OrderNotFoundException;
    @Transactional(rollbackFor = {RefundFailException.class})
    public boolean refundOrder(Long orderId) throws RefundFailException;
}
