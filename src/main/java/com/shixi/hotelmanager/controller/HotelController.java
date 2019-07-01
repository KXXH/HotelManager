package com.shixi.hotelmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.domain.DTO.HotelDTO.HotelSearchDTO;
import com.shixi.hotelmanager.exception.HotelInfoDuplicateException;
import com.shixi.hotelmanager.exception.HotelNotFoundException;
import com.shixi.hotelmanager.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/hotel")
public class HotelController {

    @Autowired
    HotelService hotelService;

    //ABANDONED
    @RequestMapping("/get")
    public Map<String,Object> get(@RequestParam(value = "current_page",defaultValue = "1") String currentPage,@RequestParam(value = "page_size",defaultValue = "20") String pageSize){
        HashMap<String,Object> m=new HashMap<>();
        m.put("status","ok");
        m.put("data",hotelService.selectByPage(Integer.parseInt(currentPage),Integer.parseInt(pageSize)));
        return m;
    }

    /**
     * 查询酒店信息接口，支持多重条件过滤，使用and或or嵌套一个condition即可。
     * 不支持条件嵌套，即and和or是线性出现在最终的SQL语句中的
     *
     * @param searchDTO
     * @return
     */
    @RequestMapping("/search")
    public Map<String,Object> search(@RequestBody HotelSearchDTO searchDTO){
        HashMap<String,Object> m=new HashMap<>();
        try{
            List<Hotel> ans=hotelService.searchHotel(
                    searchDTO.getCurrentPage(),
                    searchDTO.getSize(),
                    searchDTO.getCondition()
            );
            m.put("status","ok");
            m.put("data",ans);
            return m;
        }catch(BadSqlGrammarException e){
            m.put("status","error");
            m.put("msg","参数错误");
            return m;
        }
    }

    @RequestMapping("/admin/delHotel")
    public Map<String,Object> delHotel(@RequestBody List<Integer> delIds){
        int count=hotelService.delHotel(delIds);
        HashMap<String,Object> m=new HashMap<>();
        m.put("status","ok");
        m.put("count",count);
        return m;
    }
    @RequestMapping(value = "addHotel")
    public Map<String,Object> addHotel(@Valid Hotel hotel, BindingResult bindingResult){
        HashMap<String,Object> m = new HashMap<>();
        if(bindingResult.hasErrors()){
            m.put("msg",bindingResult.getAllErrors());
            m.put("status","error");
            return m;
        }
        try {
            boolean flag = hotelService.addHotel(hotel);
            if(flag){
                m.put("status","ok");
                m.put("msg","增加成功！");
                return m;
            }
        }catch (HotelInfoDuplicateException e){
            m.put("status","error");
            m.put("msg","酒店参数重复！");
            return m;
        }
        return null;
    }

    @RequestMapping(value = "updateHotel")
    public Map<String,Object> updateHotel(@Valid Hotel hotel,BindingResult bindingResult){
        HashMap<String,Object> m = new HashMap<>();
        if(bindingResult.hasErrors()){
            m.put("msg",bindingResult.getAllErrors());
            m.put("status","error");
            return m;
        }
        try {
            boolean flag = hotelService.updateHotel(hotel);
            if(flag){
                m.put("status","ok");
                m.put("msg","修改成功！");
                return m;
            }
        }catch (HotelInfoDuplicateException e){
            m.put("status","error");
            m.put("msg","酒店参数重复！");
            return m;
        } catch (HotelNotFoundException e) {
            m.put("status","error");
            m.put("msg","酒店不存在！");
            return m;
        }
        return null;
    }

    @RequestMapping("/remain")
    public List<Hotel> remain(@RequestParam("startDate") String startDate,@RequestParam("endDate") String endDate){

        QueryWrapper<Hotel> wrapper=new QueryWrapper<>();
        return hotelService.selectHotelByRemain(startDate,endDate,wrapper);

    }

}
