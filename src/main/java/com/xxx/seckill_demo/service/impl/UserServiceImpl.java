package com.xxx.seckill_demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.seckill_demo.exception.GlobalException;
import com.xxx.seckill_demo.mapper.UserMapper;
import com.xxx.seckill_demo.pojo.User;
import com.xxx.seckill_demo.service.IUserService;
import com.xxx.seckill_demo.utils.CookieUtil;
import com.xxx.seckill_demo.utils.MD5Util;
import com.xxx.seckill_demo.utils.UUIDUtil;
import com.xxx.seckill_demo.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yiyinghong
 * @since 2021-09-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
//            return RespBean.error(RespBeanEnum.LOGINVO_ERROR);
//        }
//        if (!ValidatorUtil.isMobile(mobile)) {
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
        if (null == user) {
            //return RespBean.error(RespBeanEnum.LOGIN_ERROR);
            throw  new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //校验密码
        if (!MD5Util.formPassToDBPass(password, user.getSalt()).equals(user.getPassword())) {
            throw  new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

//        //生产Cookie 形成session 到redis 去 但是不必如此
//        String ticket = UUIDUtil.uuid();
//        request.getSession().setAttribute(ticket, user);
//        CookieUtil.setCookie(request, response, "userTicket", ticket);
       // return RespBean.success();




        //生产Cookie  直接到把用户的信息注入到redis 中去
        String ticket = UUIDUtil.uuid();
        redisTemplate.opsForValue().set("user"+ticket,user);
       // request.getSession().setAttribute(ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);
        return RespBean.success(ticket);



    }

    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response) {
        if(StringUtils.isEmpty(userTicket)){
            return null;

        }

       User user = (User) redisTemplate.opsForValue().get("user"+userTicket);
        if(user!=null){
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }

        return user;
    }

    @Override
    public RespBean updatePassword(String userTicket, String password, HttpServletRequest request, HttpServletResponse response) {
        User userByCookie = getUserByCookie(userTicket,request,response);
        //判断用户是否为空
        if(null==userByCookie){
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);


        }
        userByCookie.setPassword(MD5Util.inputPassToDbPass(password,userByCookie.getSalt()));
        int result = userMapper.updateById(userByCookie);
        if(1==result){
            redisTemplate.delete("user:"+userTicket);
            return RespBean.success();
        }
        else{
            return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
        }
       // return null;
    }

}

