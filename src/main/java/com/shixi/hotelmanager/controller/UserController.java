package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.DTO.DefaultReturnDTO;
import com.shixi.hotelmanager.domain.DTO.DefaultSuccessDTO;
import com.shixi.hotelmanager.domain.DTO.UserDTO.ChangePasswdDTO;
import com.shixi.hotelmanager.domain.DTO.UserDTO.ForgetPasswordDTO;
import com.shixi.hotelmanager.domain.DTO.UserDTO.UserDeleteDTO;
import com.shixi.hotelmanager.domain.DTO.VerificationDTO.VerificationFailDTO;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.domain.UserDetail;
import com.shixi.hotelmanager.exception.InsufficientPermissionException;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.exception.VerificationFailException;
import com.shixi.hotelmanager.mapper.UserMapper;
import com.shixi.hotelmanager.service.UserService;
import com.shixi.hotelmanager.validation.UpdateTelephoneValudation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.*;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public Authentication getUserId() {
        User user = ((UserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Set<String> roles = AuthorityUtils.authorityListToSet(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        System.out.println(roles);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @RequestMapping("/get")
    public List<User> get(){
        return userMapper.selectList(null);
    }

    @RequestMapping("/admin/addUser")
    public Map<String,Object> addUser(
            @Valid User user,BindingResult result
    ){
        HashMap<String,Object> m=new HashMap<>();
        if(result.hasErrors()){
            m.put("status","error");
            m.put("msg",result.getAllErrors());
            return m;
        }
        try{
            userService.addUser(user);
        }catch(UserInfoDuplicateException e){
            m.put("status","error");
            m.put("msg","用户信息重复");
            return m;
        }
        return m;
    }

    @RequestMapping(value = "admin/simple_select")
    public Map<String,Object> selectByCondition(@Valid Condition condition, BindingResult bindingResult){
        Map<String,Object> m = new HashMap<>();
        if(bindingResult.hasErrors()){
            m.put("status","-1");
            m.put("msg",bindingResult.getAllErrors());
            return m;
        }
        List<User> users = userService.selectByMap(condition);
        if(users.size()>0){
            m.put("status","1");
            m.put("msg","查询用户成功");
            m.put("users",users);
        }else{
            m.put("status","0");
            m.put("msg","查询用户为空");
        }
        return m;
    }

    @RequestMapping("/admin/updateUser")
    public DefaultReturnDTO updateUser(@Valid User user,BindingResult result) throws UserNotFoundException, UserInfoDuplicateException, InsufficientPermissionException {
        HashMap<String,Object> m=new HashMap<>();
        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().iterator().next().toString());
        }
        if(user.getId()==0){
            throw new ValidationException("必须传入ID");
        }
        else {
            userService.updateUser(user);
            return new DefaultSuccessDTO();
        }
    }

    @RequestMapping("/super_admin/updateUser")
    public DefaultReturnDTO updateUserWithSA(@Valid User user,BindingResult result) throws UserNotFoundException, UserInfoDuplicateException, InsufficientPermissionException {
        HashMap<String,Object> m=new HashMap<>();
        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().iterator().next().toString());
        }
        if(user.getId()==0){
            throw new ValidationException("必须传入ID");
        }
        else {
            userService.updateUserWithSA(user);
            return new DefaultSuccessDTO();
        }
    }

    @RequestMapping(value = "admin/delete")
    public Map<String,String> deleteUser(@RequestParam int id){
        System.out.println(id);
        Map<String,String> m = new HashMap<>();
        try{
            boolean flag = userService.deleteByid(id);
            if (flag){
                m.put("status","1");
                m.put("msg","删除用户成功");
            }else {
                m.put("status","0");
                m.put("msg","删除用户失败");
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            m.put("status","-1");
            m.put("msg","用户不存在");
        }
        return m;
    }

    @RequestMapping(value = "admin/muiltDelete")
    public Map<String,String> deleteUsers(@RequestBody UserDeleteDTO userDeleteDTO){
        ArrayList ids = userDeleteDTO.getIds();
        Map<String,String> m = new HashMap<>();
        int result = userService.deleteByids(ids);
        m.put("status","1");
        m.put("msg","已删除"+result+"条!");
        return m;
    }

    @RequestMapping(value = "changePasswd")
    public Map<String,String> changePassword(@Valid ChangePasswdDTO changePasswdDTO,BindingResult result){
        Map<String,String> m = new HashMap<>();
        if (result.hasErrors()){
            m.put("status","error");
            m.put("msg", String.valueOf(result.getAllErrors()));
            return m;
        }
        System.out.println(changePasswdDTO.getOldPassword());
        int flag = userService.changePasswd(changePasswdDTO);
        if(flag==1){
            m.put("status","ok");
            m.put("msg","修改密码成功");
        }
        else if (flag == 2){
            m.put("status","error");
            m.put("msg","密码不一致!");
        }
        else if (flag == 3){
            m.put("status","error");
            m.put("msg","原密码不正确!");
        }
        return m;
    }
    @RequestMapping("/updateUserinfo")
    public DefaultReturnDTO updateUser(User user) throws UserInfoDuplicateException, UserNotFoundException {
        userService.updateUserInfo(user);
        return new DefaultSuccessDTO();
    }


    @RequestMapping("/updateTelephone")
    public DefaultReturnDTO updateTelephone(
            @Validated({UpdateTelephoneValudation.class}) User user,
            BindingResult result,
            @RequestParam("code") int code,
            HttpSession session
            ) throws UserNotFoundException, UserInfoDuplicateException {
        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().iterator().next().toString());
        }
        if(userService.updateTelephone(user,code,session.getId())){
            return new DefaultSuccessDTO();
        }else{
            return new VerificationFailDTO();
            //ttt
        }
    }


    @RequestMapping("/forgetPassword")
    public DefaultReturnDTO forgetPassword(@Valid ForgetPasswordDTO forgetPasswordDTO, BindingResult bindingResult,HttpSession session) throws UserNotFoundException, UserInfoDuplicateException, VerificationFailException {
        if(bindingResult.hasErrors()){
            throw new ValidationException(bindingResult.getAllErrors().iterator().next().toString());
        }
        userService.forgetPasssword(
                forgetPasswordDTO.getTelephone(),
                forgetPasswordDTO.getNewPassword(),
                forgetPasswordDTO.getCode(),
                session.getId()
        );
        return new DefaultSuccessDTO();
    }
}
