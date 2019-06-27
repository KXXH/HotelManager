package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.DTO.DefaultReturnDTO;
import com.shixi.hotelmanager.domain.DTO.DefaultSuccessDTO;
import com.shixi.hotelmanager.domain.DTO.RequestTooFrequentDTO;
import com.shixi.hotelmanager.domain.DTO.VerificationDTO.VerificationDTO;
import com.shixi.hotelmanager.domain.DTO.VerificationDTO.VerificationFailDTO;
import com.shixi.hotelmanager.domain.DTO.VerificationDTO.VerificationSuccessDTO;
import com.shixi.hotelmanager.exception.VerificationCodeQueryTooFrequent;
import com.shixi.hotelmanager.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashMap;

@RestController
@RequestMapping("/verification")
public class VerificationController {
    @Autowired
    VerificationCodeService verificationCodeService;

    @RequestMapping("/sendCode")
    public DefaultReturnDTO sendCode(
            @RequestParam("telephone")
            @NotBlank
            @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "手机号码格式不正确")
                    String telephone,
            HttpSession session){
        try{
            verificationCodeService.sendVerification(telephone,session.getId());
        }catch (VerificationCodeQueryTooFrequent e){
            return new RequestTooFrequentDTO();
        }
        return new DefaultSuccessDTO();
    }

    @RequestMapping("/validateCode")
    public VerificationDTO validateCode(
            @RequestParam("telephone")
            @NotBlank
            @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "手机号码格式不正确")
                    String telephone,
            @RequestParam("code")
            @NotBlank
            @DecimalMax(value="9999")
            @DecimalMin(value="1000")
                    String strCode,
            HttpSession session
    ){
        HashMap<String,Object> m=new HashMap<>();
        if(verificationCodeService.verificationCode(Integer.parseInt(strCode),telephone,session.getId())){
            return new VerificationSuccessDTO();
        }else{
            return new VerificationFailDTO();
        }
    }

}
