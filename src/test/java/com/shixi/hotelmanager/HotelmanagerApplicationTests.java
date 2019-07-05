package com.shixi.hotelmanager;

import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderDTO;
import com.shixi.hotelmanager.exception.HotelRoomInsufficientException;
import com.shixi.hotelmanager.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.bind.ValidationException;
import java.text.ParseException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HotelmanagerApplicationTests {



    @Test
    public void contextLoads() {
    }

    @Autowired
    OrderService orderService;
    @Test
    public void testForCreatOrder() throws ValidationException {
        CreateOrderDTO dto = new CreateOrderDTO();
        dto.setDateStart("2019-07-01");
        dto.setDateEnd("2019-07-07");
        dto.setOrderRoomId(432);
        dto.setRoomCount(1);
        dto.setTelephone("13456747075");
        dto.setPeopleCount(2);
        dto.setPersonName("zjm");
        try {
            orderService.createOrder(dto);
        } catch (HotelRoomInsufficientException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
