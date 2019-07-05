package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.domain.HotelRoom;
import com.shixi.hotelmanager.domain.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;

@Data
@NoArgsConstructor
public class OrderReturnDTO extends OrderDTO {
    private long id;
    private String hotelChineseName;
    private String hotelEnglishName;
    private double price;
    private int count;
    private String dateStart;
    private String dateEnd;
    private String status;
    private String roomName;
    private String createTime;
    public OrderReturnDTO(Order order){
        Hotel hotel=new Hotel();
        HotelRoom room=new HotelRoom();
        room=room.selectById(order.getOrderRoomId());
        QueryWrapper<Hotel> hotelQueryWrapper=new QueryWrapper<>();
        hotelQueryWrapper.eq("hotel_id",order.getHotelId());
        hotel=hotel.selectOne((Wrapper)hotelQueryWrapper);
        setHotelChineseName(hotel.getHotelTranslatedName());
        setHotelEnglishName(hotel.getHotelName());
        setCount(order.getRoomCount());
        setPrice(order.getPrice());
        setDateStart(order.getDateStart());
        setDateEnd(order.getDateEnd());
        setId(order.getId());
        if(order.getCreateTime()!=null)
            setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(order.getCreateTime()));
        else
            setCreateTime("未知");
        switch(order.getStatus()){
            case "UNPAID":
                setStatus("未支付");
                break;
            case "PAID":
                setStatus("已支付");
                break;
            case "REFUND":
                setStatus("已退款");
                break;
            case "CANCEL":
                setStatus("已取消");
                break;
        }
        setRoomName(room.getBedType());
    }
}
