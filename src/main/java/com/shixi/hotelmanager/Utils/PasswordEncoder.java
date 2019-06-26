package com.shixi.hotelmanager.Utils;

public class PasswordEncoder implements org.springframework.security.crypto.password.PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword){
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword,String encodeedPassword){
        return encodeedPassword.equals((String)rawPassword);
    }
}
