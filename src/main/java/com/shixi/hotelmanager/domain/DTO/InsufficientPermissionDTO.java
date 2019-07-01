package com.shixi.hotelmanager.domain.DTO;

public class InsufficientPermissionDTO extends ErrorReturnDTO {
    public InsufficientPermissionDTO(){
        setCode(789);
        setMsg("您试图进行的操作需要高级权限");
    }
}
