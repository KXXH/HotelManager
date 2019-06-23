package com.shixi.hotelmanager;

import com.shixi.hotelmanager.mapper.userMapper;
import com.shixi.hotelmanager.entity.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@MapperScan("com.shixi.hotelmanager.mapper")
public class HotelmanagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelmanagerApplication.class, args);
    }

}
