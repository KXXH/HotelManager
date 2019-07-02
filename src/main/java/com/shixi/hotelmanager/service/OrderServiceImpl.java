package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.domain.*;
import com.shixi.hotelmanager.mapper.OrderMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Override
    @Transactional
    public boolean createOrder(CreateOrderDTO dto) {
        Order order=new Order();
        //填写订单基本信息
        order.setOrderRoomId(dto.getOrderRoomId());
        order.setRoomCount(dto.getRoomCount());
        order.setDateStart(dto.getDateStart());
        order.setDateEnd(dto.getDateEnd());
        order.setTelephone(dto.getTelephone());
        order.setPersonName(dto.getPersonName());
        order.setPeopleCount(dto.getPeopleCount());
        //找到操作用户并将用户和订单关联
        User opUser= ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        order.setOrderUserId(opUser.getUserId());

        //找到对应酒店房间并关联
        Room room=new Room();
        room=room.selectById(order.getOrderRoomId());
        order.setPrice(room.getPrice());
        order.setBreakfast(room.getBreakfast());
        order.setWindows(room.getWindows());

        Hotel hotel=new Hotel();
        hotel=hotel.selectById(room.getTheRoomHotelId());
        order.setHotelName(hotel.getHotelName());

        //设置订单当前状态为未支付
        order.setStatus("UNPAID");

        //保存订单
        save(order);

        //TODO:为房间状态和酒店状态数据表加锁

        //写房间数据库

        
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
