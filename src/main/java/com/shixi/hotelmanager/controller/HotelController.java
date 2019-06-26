package com.shixi.hotelmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.domain.HotelSearchConditionType;
import com.shixi.hotelmanager.mapper.HotelMapper;
import com.shixi.hotelmanager.service.HotelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    HotelMapper hotelMapper;

    @Autowired
    HotelServiceImpl hotelService;

    @RequestMapping("/get")
    public Map<String,Object> get(@RequestParam(value = "current_page",defaultValue = "1") String currentPage,@RequestParam(value = "page_size",defaultValue = "20") String pageSize){
        HashMap<String,Object> m=new HashMap<>();
        m.put("status","ok");
        m.put("data",hotelService.selectByPage(Integer.parseInt(currentPage),Integer.parseInt(pageSize),hotelMapper));
        return m;
    }

    @RequestMapping("/search")
    public List<Hotel> search(@RequestBody HotelSearchConditionType conditionType){
        QueryWrapper<Hotel> wrapper=new QueryWrapper<>();
        System.out.println("Target: "+conditionType.getTarget());
        System.out.println("eq: "+conditionType.getEq());
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
        List<Hotel> ans=hotelMapper.selectList(wrapper);
        return ans;
    }
    private QueryWrapper<Hotel> setCondition(HotelSearchConditionType conditionType, QueryWrapper<Hotel> wrapper){
        wrapper.le(conditionType.getHigh()!=null,conditionType.getTarget(),conditionType.getHigh());
        wrapper.ge(conditionType.getLow()!=null,conditionType.getTarget(),conditionType.getLow());
        wrapper.like(conditionType.getLike()!=null,conditionType.getTarget(),conditionType.getLike());
        wrapper.eq(conditionType.getEq()!=null,conditionType.getTarget(),conditionType.getEq());
        return wrapper;
    }
}
