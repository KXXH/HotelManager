package com.shixi.hotelmanager.controller;

import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.mapper.UserMapper;
import com.shixi.hotelmanager.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.shixi.hotelmanager.mapper.userMapper;
import com.shixi.hotelmanager.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/user")
@RestController
public class UserController {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserServiceImpl userService = new UserServiceImpl();

    @GetMapping("/getVerificationCode")
    public String code(){
        return "123";
    }

    @RequestMapping("/addUser")
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
            userService.addUser(user,userMapper);
        }catch(UserInfoDuplicateException e){
            m.put("status","error");
            m.put("msg","用户信息重复");
            return m;
        }
        return m;
    }

    @RequestMapping(value = "simple_select")
    public Map<String,Object> selectByCondition(@Valid Condition condition, BindingResult bindingResult){
        Map<String,Object> m = new HashMap<>();
        if(bindingResult.hasErrors()){
            m.put("status","-1");
            m.put("msg",bindingResult.getAllErrors());
            return m;
        }
        List<User> users = userService.selectByMap(condition,userMapper);
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
    @GetMapping("/select/{name}")
    public List<User> select(@PathVariable String name){
        Map<String,Object> map=new HashMap<>();

        map.put("username",name);
        List<User> users = mapper.selectByMap(map);
        return users;
    }
    @GetMapping("/del/{id}")
    public Map<String,Integer> del(@PathVariable String id){
        int count=mapper.deleteById(id);
        HashMap<String,Integer> m=new HashMap<>();
        m.put("count",count);
        return m;
    }

    @RequestMapping("/updateUser")
    public Map<String,Object> updateUser(@Valid User user,BindingResult result){
        HashMap<String,Object> m=new HashMap<>();
        if(user.getId()==0){
            m.put("status","error");
            m.put("msg","必须传入id");
            return m;
        }
        else {
            try {
                userService.updateUser(user,userMapper);
            } catch (UserNotFoundException e) {
                m.put("status","error");
                m.put("msg","用户未找到!");
                e.printStackTrace();
                return m;
            }
            m.put("status","ok");
            return m;
        }
    }

    @RequestMapping(value = "delete")
    public Map<String,String> deleteUser(@RequestParam int id){
        System.out.println(id);
        Map<String,String> m = new HashMap<>();
        try{
            boolean flag = userService.deleteByid(id,userMapper);
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

    @RequestMapping(value = "muiltDelete")
    public Map<String,String> deleteUsers(@RequestBody Map<String,Object> map){
        ArrayList ids = (ArrayList) map.get("data");
        Map<String,String> m = new HashMap<>();
        int result = userService.deleteByids(ids,userMapper);
        m.put("status","1");
        m.put("msg","已删除"+result+"条!");
        return m;
    }
}
