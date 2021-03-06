package com.shixi.hotelmanager;

import com.shixi.hotelmanager.domain.DTO.InsufficientPermissionDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.BadOrderStatusDTO;
import com.shixi.hotelmanager.domain.DTO.OrderDTO.CreateOrderFailDTO;
import com.shixi.hotelmanager.domain.DTO.UserDTO.UserInfoDuplicateDTO;
import com.shixi.hotelmanager.domain.DTO.UserDTO.UserNotFoundDTO;
import com.shixi.hotelmanager.domain.DTO.VerificationDTO.VerificationFailDTO;
import com.shixi.hotelmanager.exception.*;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Component
public class GlobalExceptionHandler {
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handle(ValidationException exception) {
        HashMap<String,String>m=new HashMap<>();
        m.put("status","error");
        m.put("msg",exception.getMessage());
        System.out.println("bad request, " + exception.getMessage());
        //return "bad request, " + exception.getMessage();
        return m;
    }
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserInfoDuplicateDTO handle(UserInfoDuplicateException exception){
        return new UserInfoDuplicateDTO();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UserNotFoundDTO handle(UserNotFoundException e){
        return new UserNotFoundDTO();
    }


    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public VerificationFailDTO handle(VerificationFailException e){
        return new VerificationFailDTO();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InsufficientPermissionDTO handle(InsufficientPermissionException e){
        return new InsufficientPermissionDTO();
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public BadOrderStatusDTO handle(OrderStatusException e){
        return new BadOrderStatusDTO();
    }

    @ExceptionHandler
    public String handle(SignatureException e, Model model){
        model.addAttribute("message","支付安全校验失败！");
        return "paymentComplete";
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CreateOrderFailDTO handle(HotelRoomInsufficientException e){
        return new CreateOrderFailDTO("房间不够辣!");
    }

    @ExceptionHandler
    public String handle(OrderNotFoundException e,Model model){
        model.addAttribute("message","订单没有找到哦! ");
        return "paymentComplete";
    }

    @ExceptionHandler
    public String handle(OrderPaymentAlreadySuccessException e,Model model){
        model.addAttribute("message","您已经付款了哦，请不要重新支付");
        return "paymentComplete";
    }

}