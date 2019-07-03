package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.exception.VerificationCodeQueryTooFrequent;

public interface VerificationCodeService {
    int sendVerification(String telephone, String sessionId) throws VerificationCodeQueryTooFrequent;
    boolean verificationCode(int code,String telephone, String  sessionId);
}
