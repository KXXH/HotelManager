package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.domain.HotelSearchDTO;
import com.shixi.hotelmanager.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
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
}
