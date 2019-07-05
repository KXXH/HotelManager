package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.DTO.UserDTO.ForgetPasswordDTO;
import com.shixi.hotelmanager.domain.DTO.UserDTO.LoginPageReturnDTO;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.exception.VerificationFailException;
import com.shixi.hotelmanager.mapper.UserMapper;
import com.shixi.hotelmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.ValidationException;

@RequestMapping("/login")
@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/forgetPassword")
    public String forgetPassword(@Valid ForgetPasswordDTO forgetPasswordDTO, BindingResult bindingResult, HttpSession session, Model model) throws UserNotFoundException, UserInfoDuplicateException, VerificationFailException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().iterator().next().toString());
        }
        boolean flag = userService.forgetPasssword(
                forgetPasswordDTO.getTelephone(),
                forgetPasswordDTO.getNewPassword(),
                forgetPasswordDTO.getCode(),
                session.getId()
        );
        System.out.println(flag);
        LoginPageReturnDTO loginPageReturnDTO = new LoginPageReturnDTO();
        loginPageReturnDTO.setType("forgetPassword");
        loginPageReturnDTO.setStatus("ok");
        model.addAttribute(loginPageReturnDTO);
        return "login";
    }
}
