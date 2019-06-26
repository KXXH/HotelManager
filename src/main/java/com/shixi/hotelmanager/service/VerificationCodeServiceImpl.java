package com.shixi.hotelmanager.service;

import com.shixi.hotelmanager.exception.VerificationCodeQueryTooFrequent;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;

@Data
@AllArgsConstructor
class CodeRecord{
    private String telephone;
    private int code;
    private Long timestamp;
    private String status;
}

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService{
    private static HashMap<String,CodeRecord> registerCodeMap;

    @Override
    public int sendVerification(String telephone, String sessionId) throws VerificationCodeQueryTooFrequent {
        Random random=new Random();
        if(registerCodeMap==null) registerCodeMap=new HashMap<>();
        CodeRecord formerRecord=registerCodeMap.get(sessionId);
        if(formerRecord!=null){
            Long currentTimestamp=new Date().getTime();
            if(currentTimestamp-formerRecord.getTimestamp()<60*1000){
                throw new VerificationCodeQueryTooFrequent();
            }
        }
        int code=random.nextInt(9000)+1000;
        System.out.println(telephone+":"+code);
        registerCodeMap.put(sessionId,new CodeRecord(telephone,code,new Date().getTime(),"unchecked"));
        //TODO: send SMS Here.
        return code;
    }

    @Override
    public boolean verificationCode(int code,String telephone, String sessionId) {
        if(registerCodeMap==null) registerCodeMap=new HashMap<>();
        CodeRecord codeRecord=registerCodeMap.get(sessionId);
        Long currentTimestamp=new Date().getTime();
        if(codeRecord!=null&&currentTimestamp-codeRecord.getTimestamp()<60*1000){
            if(codeRecord.getTelephone().equals(telephone) && codeRecord.getCode() == code){
                registerCodeMap.remove(sessionId);
                return true;
            }
        }
        return false;
    }
}
