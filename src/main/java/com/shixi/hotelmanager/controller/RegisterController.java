package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/register")
@Validated
public class RegisterController {
    @Autowired
    VerificationCodeService verificationCodeService;

    @Autowired
    UserService userService;

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
            }else if(!Objects.equals(user.getRole(), "USER")){
                m.put("status","error");
                m.put("msg","用户角色不允许创建");
                return m;
            }
            else{
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
