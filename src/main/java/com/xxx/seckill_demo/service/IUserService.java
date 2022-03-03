package com.xxx.seckill_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.seckill_demo.pojo.User;
import com.xxx.seckill_demo.vo.LoginVo;
import com.xxx.seckill_demo.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yiyinghong
 * @since 2021-09-03
 */
public interface IUserService extends IService<User> {


    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    //根据cookie获取用户
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);

    //更新密码 如何保证redis与数据库一样
    RespBean updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);



}
