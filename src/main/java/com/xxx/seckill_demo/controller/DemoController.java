package com.xxx.seckill_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/demo")
//测试页面跳转

public class DemoController {
    @RequestMapping("/hello")
    public  String hello(Model model){
        model.addAttribute("name","xxx");
        return "hello";



    }

}
