package com.xxx.seckill_demo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

        //通用状态码
        SUCCESS(200,"success"),
        ERROR(500,"服务端异常"),
        //登录模块5002xx
        SESSION_ERROR(500210,"用户不存在或者已经失效"),
        LOGIN_ERROR(500211,"用户名或者密码错误"),
        MOBILE_ERROR(500212,"手机号码格式错误"),



        //秒杀模块5005xx
        EMPTY_STOCK(500500,"秒杀的库存不足"),
        REPEATE_ERROR(500501,"该商品每一个人限购一件"),


        MOBILE_NOT_EXIST(500213, "手机号码不存在"),
        PASSWORD_UPDATE_FAIL(500214, "密码更新失败"),

        //订单模块
        ORDER_NOT_EXIST(500300,"订单信息不存在"),


        BIND_ERROR(5002,"参数校验异常");
        private final Integer code;
        private final String message;

}
