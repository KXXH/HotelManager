package com.shixi.hotelmanager.domain.DTO.OrderDTO;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Hotel;
import com.shixi.hotelmanager.domain.HotelRoom;
import com.shixi.hotelmanager.domain.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderReturnDTO extends OrderDTO {
    private String hotelChineseName;
    private String hotelEnglishName;
    private double price;
    private int count;
    private String dateStart;
    private String dateEnd;
    private String status;
    public OrderReturnDTO(Order order){
        Hotel hotel=new Hotel();
        HotelRoom room=new HotelRoom();
        QueryWrapper<Hotel> hotelQueryWrapper=new QueryWrapper<>();
        hotelQueryWrapper.eq("hotel_id",order.getHotelId());
        hotel=hotel.selectOne((Wrapper)hotelQueryWrapper);
        setHotelChineseName(hotel.getHotelTranslatedName());
        setHotelEnglishName(hotel.getHotelName());
        setCount(order.getRoomCount());
        setPrice(order.getPrice());
        setDateStart(order.getDateStart());
        setDateEnd(order.getDateEnd());
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
    }
}
