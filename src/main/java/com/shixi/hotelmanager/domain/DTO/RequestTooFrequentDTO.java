package com.shixi.hotelmanager.domain.DTO;

public class RequestTooFrequentDTO extends ErrorReturnDTO {
    public RequestTooFrequentDTO(){
        setCode(999);
        setMsg("请求过于频繁!");
    }
}
