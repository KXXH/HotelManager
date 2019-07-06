package com.shixi.hotelmanager.Utils;
import org.springframework.util.DigestUtils;

/**
 * Created by zjm97 on 2019/6/1.
 */
public class MD5 {

    public static final String Key="jiudianjiudianjiudian";
    public static String md5(String text){
        String ans=DigestUtils.md5DigestAsHex((text+Key).getBytes());
        return ans;
    }
    public static  boolean verify(String text,String md5){
        return md5(text).equals(md5);
    }
}