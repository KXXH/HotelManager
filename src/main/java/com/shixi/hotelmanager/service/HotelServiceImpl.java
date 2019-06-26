package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.mapper.HotelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServiceImpl  implements HotelService {
    @Override
    public List<Hotel> selectByPage(int current, int size, HotelMapper hotelMapper) {
        Page<Hotel> page= new Page<>(current,size);
        System.out.println("current="+current+",page="+size);
        QueryWrapper<Hotel> queryWrapper=new QueryWrapper<>();
        IPage<Hotel> iPage = hotelMapper.selectPage(page,queryWrapper);
        return iPage.getRecords();
    }
}
