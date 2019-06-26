package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.exception.VerificationCodeQueryTooFrequent;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Map;



public interface VerificationCodeService {
    int sendVerification(String telephone, String sessionId) throws VerificationCodeQueryTooFrequent;
    boolean verificationCode(int code,String telephone, String  sessionId);
}
