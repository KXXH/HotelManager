package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi.hotelmanager.domain.Order;
import com.shixi.hotelmanager.mapper.OrderMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderManagerServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderManagerService{
    @Override
    public List<Order> getAllOrder(int current,int size) {
        Page<Order> page= new Page<>(current,size);
        Order order = new Order();
        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
        IPage<Order> iPage = order.selectPage(page,queryWrapper);
        return iPage.getRecords();
    }
}
