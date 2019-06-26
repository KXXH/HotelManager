package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.VerificationCodeQueryTooFrequent;
import com.shixi.hotelmanager.service.UserService;
import com.shixi.hotelmanager.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/register")
@Validated
public class RegisterController {
    @Autowired
    VerificationCodeService verificationCodeService;

    @Autowired
    UserService userService;

    @RequestMapping("/sendCode")
    public Map<String,Object> sendCode(
            @RequestParam("telephone")
            @NotBlank
            @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "手机号码格式不正确")
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
            m.put("status","ok");
            return m;
        }else{
            m.put("status","fail");
            return m;
        }
    }

    @RequestMapping("/registerUser")
    public Map<String,Object> registerUser(
            @Valid User user,
            BindingResult result,
            @RequestParam("code") @DecimalMin("1000") @DecimalMax("9999") String strCode,
            HttpSession session
    ){
        HashMap<String,Object> m=new HashMap<>();
        if(result.hasErrors()){
            m.put("status","error");
            m.put("msg",result.getAllErrors());
            return m;
        }
        else{
            if(!verificationCodeService.verificationCode(Integer.parseInt(strCode),user.getTelephone(),session.getId())){
                m.put("status","error");
                m.put("msg","验证码错误");
                return m;
            }else{
                try{
                    userService.addUser(user);
                }catch(UserInfoDuplicateException e){
                    m.put("status","error");
                    m.put("msg","用户信息重复");
                    m.put("code",111);
                    return m;
                }
                m.put("status","success");
                return m;
            }
        }
    }
}
