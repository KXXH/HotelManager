package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.Utils.GetUserInfo;
import com.shixi.hotelmanager.Utils.UpdateUserInfo;
import com.shixi.hotelmanager.domain.DTO.DefaultReturnDTO;
import com.shixi.hotelmanager.domain.DTO.DefaultSuccessDTO;
import com.shixi.hotelmanager.domain.DTO.UserDTO.ChangePasswdDTO;
import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.DTO.VerificationDTO.VerificationFailDTO;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.domain.DTO.UserDTO.UserDeleteDTO;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.mapper.UserMapper;
import com.shixi.hotelmanager.service.UserService;
import com.shixi.hotelmanager.validation.UpdateTelephoneValudation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public int getUserId() {
        User user = GetUserInfo.getInfo(userMapper);
        user.setUsername("hsj");
        userMapper.updateById(user);
        user.setUsername("hsj");
        UpdateUserInfo.update(user);
        return user.getId();
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
    public Map<String,Object> updateUser(@Valid User user,BindingResult result){
        HashMap<String,Object> m=new HashMap<>();
        if(result.hasErrors()){
            m.put("status","error");
            m.put("msg",result.getAllErrors());
            return m;
        }
        if(user.getId()==0){
            m.put("status","error");
            m.put("msg","必须传入id");
            return m;
        }
        else {
            try {
                userService.updateUser(user);
            } catch (UserNotFoundException e) {
                m.put("status","error");
                m.put("msg","用户未找到!");
                e.printStackTrace();
                return m;
            } catch (UserInfoDuplicateException e) {
                m.put("status","error");
                m.put("msg","用户信息重复");
                e.printStackTrace();
            }
            m.put("status","ok");
            return m;
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
    public Map<String,String> changePassword(ChangePasswdDTO changePasswdDTO){
        Map<String,String> m = new HashMap<>();
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
}
