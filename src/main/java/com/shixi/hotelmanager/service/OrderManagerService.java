package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.domain.Order;

import java.util.List;

public interface OrderManagerService {
    List<Order> getAllOrder(int current,int size);
}
