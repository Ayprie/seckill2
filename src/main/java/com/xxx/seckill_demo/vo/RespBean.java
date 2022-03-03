package com.xxx.seckill_demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {

    private long code; //状态码
    private String message;
    private Object obj;

    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(),null);
    }

    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(),obj);
    }

    public static RespBean error(RespBeanEnum resBeanEnum){
        return new RespBean(resBeanEnum.getCode(),resBeanEnum.getMessage(),null);
    }

    public static RespBean error(RespBeanEnum resBeanEnum, Object obj){
        return new RespBean(resBeanEnum.getCode(),resBeanEnum.getMessage(),obj);
    }

}
