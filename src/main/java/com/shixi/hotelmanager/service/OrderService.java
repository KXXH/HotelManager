package com.shixi.hotelmanager.service;

import com.alipay.api.AlipayApiException;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchConditionType;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.exception.HotelRoomInsufficientException;
import com.shixi.hotelmanager.exception.OrderNotFoundException;
import com.shixi.hotelmanager.exception.OrderStatusException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

public interface OrderService {
    @Transactional(rollbackFor = {HotelRoomInsufficientException.class})
    boolean createOrder(CreateOrderDTO dto) throws HotelRoomInsufficientException, ParseException;
    String payOrder(Long orderId) throws OrderNotFoundException, UserNotFoundException, OrderStatusException;
    boolean payOrderComplete(Long orderId,String tradeNo) throws OrderNotFoundException, OrderStatusException;
    @Transactional
    boolean refundOrder(Long orderId);
    String checkPaymentStatus(Long orderId) throws OrderNotFoundException, AlipayApiException;
    List<Order> searchOrder(int currentPage, int size, OrderSearchConditionType condition);

}
