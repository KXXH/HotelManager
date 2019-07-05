package com.shixi.hotelmanager.service;

import com.alipay.api.AlipayApiException;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.OrderSearchConditionType;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.exception.*;
import com.shixi.hotelmanager.exception.HotelRoomInsufficientException;
import com.shixi.hotelmanager.exception.OrderNotFoundException;
import com.shixi.hotelmanager.exception.OrderStatusException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.ValidationException;
import java.text.ParseException;
import java.util.List;

public interface OrderService {
    @Transactional(rollbackFor = {HotelRoomInsufficientException.class})
    Order createOrder(CreateOrderDTO dto) throws HotelRoomInsufficientException, ParseException, ValidationException;
    String payOrder(Long orderId) throws OrderNotFoundException, UserNotFoundException, OrderStatusException, AlipayApiException, OrderPaymentAlreadySuccessException;
    boolean payOrderComplete(Long orderId,String tradeNo) throws OrderNotFoundException, OrderStatusException;
    String checkPaymentStatus(Long orderId) throws OrderNotFoundException, AlipayApiException;
    List<Order> searchOrder(int currentPage, int size, OrderSearchConditionType condition) throws UserNotFoundException;
    @Transactional(rollbackFor = {RefundFailException.class})
    boolean refundOrder(Long Id,String orderStatus) throws RefundFailException, OrderNotFoundException;
    boolean makeFundOrder(Order order) throws AlipayApiException, OrderNotFoundException, RefundFailException, OutdatedOrdersException;
    boolean makeEvaluate(String evaluate,String OrderId) throws OrderNotFoundException;
}
