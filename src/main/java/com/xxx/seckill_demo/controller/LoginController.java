package com.xxx.seckill_demo.controller;

import com.xxx.seckill_demo.service.IUserService;
import com.xxx.seckill_demo.vo.LoginVo;
import com.xxx.seckill_demo.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
//import org.springframework.data.redis.RedisConnectionFailureException;





@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {

    @Autowired
    private IUserService userService;




    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }



    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
      //  log.info("{}",loginVo);

        return userService.doLogin(loginVo,request,response);
    }

}



//  log.info("{}",loginVo);
