package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.exception.VerificationCodeQueryTooFrequent;
import com.shixi.hotelmanager.service.VerificationCodeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
@Validated
public class RegisterController {
    @Autowired
    VerificationCodeServiceImpl verificationCodeService;

    @RequestMapping("/sendCode")
    public Map<String,Object> sendCode(
            @RequestParam("telephone")
            @NotBlank

                    String telephone,
            HttpSession session){
        HashMap<String,Object> m=new HashMap<>();
        try{
            verificationCodeService.sendVerification(telephone,session.getId());
        }catch (VerificationCodeQueryTooFrequent e){
            m.put("status","error");
            m.put("msg","请求太过频繁");
            return m;
        }
        m.put("status","ok");
        return m;
    }

    @RequestMapping("/validateCode")
    public Map<String,Object> validateCode(
            @RequestParam("telephone")
            @NotBlank

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
            m.put("status","ok");
            return m;
        }else{
            m.put("status","fail");
            return m;
        }

    }
}
