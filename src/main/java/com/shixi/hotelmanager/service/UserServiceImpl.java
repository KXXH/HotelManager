package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.mapper.UserMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    UserMapper mapper;


    @Override
    public List<User> selectByMap(Condition condition, UserMapper userMapper) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String username = condition.getUsername();
        String IdCard = condition.getIdCard();
        String gender = condition.getGender();
        String telephone = condition.getTelephone();
        String email = condition.getEmail();
        queryWrapper.like("email","@");

        if(StringUtils.isNotBlank(username))
            queryWrapper.and(wrapper -> wrapper.like("username",username));
        if(StringUtils.isNotBlank(IdCard))
            queryWrapper.and(wrapper -> wrapper.like("id_card",IdCard));
        if(StringUtils.isNotBlank(gender))
            queryWrapper.and(wrapper -> wrapper.eq("gender",gender));
        if(StringUtils.isNotBlank(telephone))
            queryWrapper.and(wrapper -> wrapper.like("telephone",telephone));
        if(email.length()>1)
            queryWrapper.and(wrapper -> wrapper.like("email",email));


        logger.info("正在查询...");
        //System.out.println(userMapper);
        return userMapper.selectList(queryWrapper);
    }


    @Override
    public boolean addUser(
            @Length(max=25,min=5) @NotBlank String username,
            @Length(max=64,min=6) @NotBlank String password,
            @Length(max=8) String gender,
            @Length(min=11,max=15) String telephone,
            @Length(max=255) @Email String email,
            @Length(min=18,max=18) @NotBlank String id_card,
            @NotBlank String avatar,
            UserMapper userMapper
    ) throws UserInfoDuplicateException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username)
                .or().eq("telephone", telephone)
                .or().eq("id_card", id_card)
                .or().eq("email", email);
        if (userMapper.selectCount(queryWrapper) > 0) {
            throw new UserInfoDuplicateException();
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setAvatar(avatar);
            user.setEmail(email);
            user.setGender(gender);
            user.setIdCard(id_card);
            user.setTelephone(telephone);
            user.setUserId(123);
            try{
                userMapper.insert(user);
            }catch(DuplicateKeyException e){
                throw new UserInfoDuplicateException();
            }

            return true;
        }
    }

    @Override
    public boolean addUser(User user,UserMapper userMapper) throws UserInfoDuplicateException {
        return addUser(user.getUsername(),user.getPassword(),user.getGender(),user.getTelephone(),user.getEmail(),user.getIdCard(),user.getAvatar(),userMapper);
    }

    @Override
    public boolean updateUser(User user, UserMapper userMapper) throws UserNotFoundException, UserInfoDuplicateException {
        int count=0;
        try{
            count=userMapper.updateById(user);
        }catch(DuplicateKeyException e){
            throw new UserInfoDuplicateException();
        }
        if(count==0){
            throw new UserNotFoundException();
        }
        else{
            return true;
        }
    }
    public boolean deleteByid(int id,UserMapper userMapper) throws UserNotFoundException {
        /*
        logger.info("获取用户start...");
        // 从缓存中获取用户信息
        String key = "user_" + id;
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String test="";
        // 缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            test = operations.get(key);
            logger.info("从缓存中获取了用户 id = " + id);
        }else{
            // 缓存不存在，从 DB 中获取
            test = "test";
            // 插入缓存
            operations.set(key, test, 10, TimeUnit.SECONDS);
            logger.info("内容是"+operations.get(key));
            logger.info("向缓存中插入了用户 id = " + id);
        }
        */
        logger.info("正在查询用户！");
        User user = userMapper.selectById(id);
        if (user == null){
            logger.error("用户不存在！");
            throw new UserNotFoundException();
        }
        int result = userMapper.deleteById(id);
        if (result == 1)
            return true;
        else
            return false;
    }

    @Override
    public int deleteByids(ArrayList ids, UserMapper userMapper){
        int result = userMapper.deleteBatchIds(ids);
        logger.info("result:"+result);
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            HashMap<String ,Object> m=new HashMap<>();
            m.put("username",username);
            List<User> users=mapper.selectByMap(m);
            if(users.size()<=0){
                throw new UsernameNotFoundException("用户名不存在");
            }
            List<SimpleGrantedAuthority> authorities=new ArrayList<>();
            System.out.println(users.get(0).getRole());
            authorities.add(new SimpleGrantedAuthority(users.get(0).getRole().trim()));
            return new org.springframework.security.core.userdetails.User(users.get(0).getUsername(),users.get(0).getPassword(),authorities);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
