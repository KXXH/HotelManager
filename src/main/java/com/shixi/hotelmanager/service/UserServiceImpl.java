package com.shixi.hotelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shixi.hotelmanager.Utils.GetUserInfo;
import com.shixi.hotelmanager.Utils.MD5;
import com.shixi.hotelmanager.domain.Condition;
import com.shixi.hotelmanager.domain.DTO.UserDTO.ChangePasswdDTO;
import com.shixi.hotelmanager.domain.User;
import com.shixi.hotelmanager.domain.UserDetail;
import com.shixi.hotelmanager.exception.InsufficientPermissionException;
import com.shixi.hotelmanager.exception.UserInfoDuplicateException;
import com.shixi.hotelmanager.exception.UserNotFoundException;
import com.shixi.hotelmanager.exception.VerificationFailException;
import com.shixi.hotelmanager.mapper.UserMapper;
import io.micrometer.core.instrument.util.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private VerificationCodeService verificationCodeService;


    @Override
    public List<User> selectByMap(Condition condition) {
        UserMapper userMapper=baseMapper;
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
            @NotBlank String avatar
    ) throws UserInfoDuplicateException {
        UserMapper userMapper=baseMapper;
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
            user.setPasswordEncode(password);
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
    public boolean addUser(User user) throws UserInfoDuplicateException {
        try{
            user.setPasswordEncode(user.getPassword());
            save(user);
        }catch(DuplicateKeyException e){
            throw new UserInfoDuplicateException();
        }
        return true;
    }

    @Override
    public boolean updateUser(User user) throws UserNotFoundException, UserInfoDuplicateException, InsufficientPermissionException {
        int count=0;
        UserMapper userMapper=baseMapper;
        if(!userMapper.selectById(user.getId()).getRole().equals("USER")){
            throw new InsufficientPermissionException();
        }
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

    @Override
    public boolean updateUserWithSA(User user) throws InsufficientPermissionException, UserInfoDuplicateException, UserNotFoundException {
        if(baseMapper.selectById(user.getId()).getRole().equals("SUPER_ADMIN")){
            throw new InsufficientPermissionException();
        }
        boolean count=false;
        try{
            count=updateById(user);
        }catch(DuplicateKeyException e){
            throw new UserInfoDuplicateException();
        }
        if(!count){
            throw new UserNotFoundException();
        }else{
            return count;
        }
    }

    public boolean deleteByid(int id) throws UserNotFoundException {
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
        UserMapper userMapper=baseMapper;
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
    public int deleteByids(ArrayList ids){
        UserMapper userMapper=baseMapper;
        int result = userMapper.deleteBatchIds(ids);
        logger.info("result:"+result);
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("正在验证用户信息！");
        try{
            QueryWrapper<User> wrapper=new QueryWrapper<>();
            wrapper.eq("username",username).or().eq("telephone",username);
            List<User> users=baseMapper.selectList(wrapper);
            if(users.size()<=0){
                throw new UsernameNotFoundException("用户名不存在");
            }
            List<SimpleGrantedAuthority> authorities=new ArrayList<>();
            //System.out.println(users.get(0).getRole());
            authorities.add(new SimpleGrantedAuthority("ROLE_"+users.get(0).getRole().trim()));
            return new UserDetail(authorities,users.get(0));
            //return new org.springframework.security.core.userdetails.User(users.get(0).getUsername(),users.get(0).getPassword(),authorities);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int changePasswd(ChangePasswdDTO changePasswdDTO){
        User user=((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        String realOldPasswd = user.getPassword();
        logger.info("旧密码:"+realOldPasswd);
        logger.info("输入旧密码:"+changePasswdDTO.getOldPassword());

        if (MD5.verify(changePasswdDTO.getOldPassword(),realOldPasswd)){
            if(changePasswdDTO.getNewPassword().equals(changePasswdDTO.getConfirmation())){
                user.setPasswordEncode(changePasswdDTO.getNewPassword());
                baseMapper.updateById(user);
                return 1;
            }
            else
                return 2;
        }
        else
            return 3;
    }
    public boolean updateUserInfo(User user) throws UserInfoDuplicateException, UserNotFoundException {
        //User currentUser= GetUserInfo.getInfo(baseMapper);
        User currentUser=((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        User LastUser = new User();
        setLastUser(currentUser,LastUser);
        try {
            LastUser = (User)currentUser.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if(currentUser == null){
            throw new UserNotFoundException();
        }
        if(user.getUsername()!=null){
            currentUser.setUsername(user.getUsername());
        }
        if(user.getEmail()!=null){
            currentUser.setEmail(user.getEmail());
        }
        if(user.getGender()!=null){
            currentUser.setGender(user.getGender());
        }
        if(user.getAvatar()!=null){
            currentUser.setAvatar(user.getAvatar());
        }
        Validator validator= Validation.byDefaultProvider().configure().messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("error"))).buildValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> constraintViolations=validator.validate(currentUser);
        if(constraintViolations.size()>0){
            setLastUser(LastUser,currentUser);
            System.out.println(currentUser.getUsername());
            System.out.println("已恢复");
            throw new ValidationException(constraintViolations.iterator().next().getMessage());
        }
        try{
            saveOrUpdate(currentUser);
        }catch(DuplicateKeyException e){
            setLastUser(LastUser,currentUser);
            throw new UserInfoDuplicateException();
        }
        return true;
    }

    @Override
    public boolean updateTelephone(User user,int code,String sessionId) throws UserNotFoundException, UserInfoDuplicateException {
        User currentUser= GetUserInfo.getInfo(baseMapper);
        if(currentUser==null){
            throw new UserNotFoundException();
        }
        String phone=user.getTelephone();
        assert phone != null;
        if(verificationCodeService.verificationCode(code,phone,sessionId)){
            currentUser.setTelephone(phone);
            try{
                saveOrUpdate(currentUser);
            }catch(DuplicateKeyException e){
                throw new UserInfoDuplicateException();
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean forgetPasssword(String telephone, String newPassword, int code, String sessionId) throws VerificationFailException, UserNotFoundException, UserInfoDuplicateException {
        if(!verificationCodeService.verificationCode(code,telephone,sessionId)){
            throw new VerificationFailException();
        }
        else{
            try{
                QueryWrapper<User> wrapper=new QueryWrapper<>();
                wrapper.eq("telephone",telephone);
                User user=getOne(wrapper,true);
                if(user==null){
                    throw new UserNotFoundException();
                }
                user.setPasswordEncode(newPassword);
                updateById(user);
                return true;
            }catch(DuplicateKeyException e){
                throw new UserInfoDuplicateException();
            }
        }
    }

    public void setLastUser(User currentUser,User user){
        user.setUsername(currentUser.getUsername());
        user.setEmail(currentUser.getEmail());
        user.setGender(currentUser.getGender());
        user.setAvatar(currentUser.getAvatar());
    }

}
