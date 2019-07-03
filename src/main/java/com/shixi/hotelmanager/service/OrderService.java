package com.shixi.hotelmanager.service;

import com.alipay.api.AlipayApiException;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.exception.*;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;

public interface OrderService {
    @Transactional(rollbackFor = {HotelRoomInsufficientException.class})
    boolean createOrder(CreateOrderDTO dto) throws HotelRoomInsufficientException, ParseException;
    String payOrder(Long orderId) throws OrderNotFoundException, UserNotFoundException, OrderStatusException;
    boolean payOrderComplete(Long orderId,String tradeNo) throws OrderNotFoundException;
    @Transactional(rollbackFor = {RefundFailException.class})
    boolean refundOrder(Long Id,String orderStatus) throws RefundFailException, OrderNotFoundException;
    boolean makeFundOrder(Order order) throws AlipayApiException;
}
