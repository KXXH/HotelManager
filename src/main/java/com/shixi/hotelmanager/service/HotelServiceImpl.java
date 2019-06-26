package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.domain.HotelSearchConditionType;
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

    @Override
    public List<Hotel> searchHotel(int current, int size, HotelSearchConditionType conditionType, HotelMapper hotelMapper) {
        Page<Hotel> page= new Page<>(current,size);
        QueryWrapper<Hotel> wrapper=new QueryWrapper<>();
        wrapper=setCondition(conditionType,wrapper);
        while(conditionType.getAnd()!=null||conditionType.getOr()!=null){
            if(conditionType.getOr()!=null){
                conditionType = conditionType.getOr();
                wrapper.or();
                wrapper=setCondition(conditionType,wrapper);
            }else{
                conditionType = conditionType.getAnd();
                wrapper=setCondition(conditionType,wrapper);
            }
        }
        return hotelMapper.selectPage(page,wrapper).getRecords();
    }

    private QueryWrapper<Hotel> setCondition(HotelSearchConditionType conditionType, QueryWrapper<Hotel> wrapper){
        if(conditionType==null) return wrapper;
        wrapper.le(conditionType.getHigh()!=null,conditionType.getTarget(),conditionType.getHigh());
        wrapper.ge(conditionType.getLow()!=null,conditionType.getTarget(),conditionType.getLow());
        wrapper.like(conditionType.getLike()!=null,conditionType.getTarget(),conditionType.getLike());
        wrapper.eq(conditionType.getEq()!=null,conditionType.getTarget(),conditionType.getEq());
        wrapper.orderByAsc(conditionType.getOrderByAsc()!=null,conditionType.getOrderByAsc());
        wrapper.orderByDesc(conditionType.getOrderByDesc()!=null,conditionType.getOrderByDesc());
        return wrapper;
    }
}
