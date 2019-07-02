package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.domain.UserDetail;
import com.shixi.hotelmanager.mapper.OrderMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    public boolean createOrder(CreateOrderDTO dto) {
        Order order=new Order();
        //
        order.setOrderRoomId(dto.getOrderRoomId());
        order.setRoomCount(dto.getRoomCount());
        order.setDateStart(dto.getDateStart());
        order.setDateEnd(dto.getDateEnd());
        order.setTelephone(dto.getTelephone());
        order.setPersonName(dto.getPersonName());
        order.setPeopleCount(dto.getPeopleCount());

        User opUser= ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        order.setOrderUserId(opUser.getUserId());


        return false;
    }

    @Override
    public boolean payOrder(Long orderId) {
        return false;
    }

    @Override
    public boolean refundOrder(Long orderId) {
        return false;
    }
}
