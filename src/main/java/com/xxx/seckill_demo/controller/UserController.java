package com.xxx.seckill_demo.controller;


import com.xxx.seckill_demo.pojo.User;
import com.xxx.seckill_demo.rabbitmq.MQSender;
import com.xxx.seckill_demo.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yiyinghong

 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private MQSender mqSender;



    /**
     * 用户信息，用作测试
     * @param user
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }

//
//    /**
//     * 测试发送
//     *
//     */
//    @RequestMapping("/mq")
//    @ResponseBody
//    public void mq(){
//        mqSender.send("hello");
//    }
//
//    /**
//     * 测试fanout 模式
//     */
//    @RequestMapping("/mq/fanout")
//    @ResponseBody
//    public void mq01(){
//        mqSender.send("fanout: hello");
//    }
//
//    /**(
//     * direct模式
//     */
//    @RequestMapping("/mq/direct01")
//    @ResponseBody
//    public void mq01Direct(){
//        mqSender.send01("direct: red");
//    }
//
//    @RequestMapping("/mq/direct02")
//    @ResponseBody
//    public void mq02Direct(){
//        mqSender.send02("direct: green");
//    }
//
//    /*
//    topic模式
//     */
//
//    @RequestMapping("/mq/topic01")
//    @ResponseBody
//    public void mq01Topic(){
//        mqSender.send03("topic : red");
//    }
//
//    @RequestMapping("/mq/topic02")
//    @ResponseBody
//    public void mq02Topic(){
//        mqSender.send04("topic: green");
//    }

        /*
    header模式
//     */
//        @RequestMapping("/mq/header01")
//        @ResponseBody
//        public void mq01_header() {
//            mqSender.send05("Hello,header01");
//        }
//    /**
//     * 测试发送RabbitMQ消息
//     */
//    @RequestMapping("/mq/header02")
//    @ResponseBody
//    public void mq02_header() {
//        mqSender.send06("Hello,header02");
//    }

}
