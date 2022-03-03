package com.xxx.seckill_demo.controller;


import com.xxx.seckill_demo.pojo.User;
import com.xxx.seckill_demo.service.IOrderService;
import com.xxx.seckill_demo.vo.OrderDetailVo;
import com.xxx.seckill_demo.vo.RespBean;
import com.xxx.seckill_demo.vo.RespBeanEnum;
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
* @since 2021-09-10

 *
 */
@Controller
@RequestMapping("/order")
@ResponseBody
public class OrderController {
    @Autowired
    private IOrderService orderService;



    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId){

        if(user==null){
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail = orderService.detail(orderId);
        return RespBean.success(detail);
    }

}
