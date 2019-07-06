package com.shixi.hotelmanager.Utils;

public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword){

        return MD5.md5(rawPassword.toString());
        //return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword,String encodedPassword){
        //System.out.println(encodeedPassword);
        //System.out.println(rawPassword);
        return MD5.verify(rawPassword.toString(),encodedPassword);
    }
}
