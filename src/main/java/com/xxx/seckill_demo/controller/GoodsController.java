package com.xxx.seckill_demo.controller;

import com.xxx.seckill_demo.pojo.User;
import com.xxx.seckill_demo.service.IGoodsService;
import com.xxx.seckill_demo.service.IUserService;
import com.xxx.seckill_demo.vo.DetailVo;
import com.xxx.seckill_demo.vo.GoodsVo;
import com.xxx.seckill_demo.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
  登录成功的时候，跳转到上屏展示页，否则还是在其登录页面
 windows 优化前 409
 linux 优化前
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;





    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model,User user,HttpServletRequest request,HttpServletResponse response){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if(!StringUtils.isEmpty(html))
            return html;

//        if(StringUtils.isEmpty(ticket))
//            return "login";
//
//
//        //User user = (User)session.getAttribute(ticket);
//
//
//        User user = userService.getUserByCookie(ticket,request,response);
//        if (null==user)
//            return "login";

        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodsVo());
       // return "goodsList";

        //手动渲染页面
        WebContext webContext = new WebContext(request,response,request.getServletContext(),request.getLocale(), model.asMap());
        html =  thymeleafViewResolver.getTemplateEngine().process("goodsList",webContext);
        if(!StringUtils.isEmpty(html)){
            valueOperations.set("goodsList",html,60, TimeUnit.SECONDS);
        }


        return html;
    }

   //" /goods/detail/"

    /**
     * 跳转商品详情页面
     *

     * @param model
     * @param user
     * @param goodsId
     * @return
     */
   @RequestMapping(value = "/detail/{goodsId}")
   @ResponseBody
   public RespBean toDetail( Model model, User user,
                            @PathVariable Long goodsId) {
       GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
       Date startDate = goods.getStartDate();
       Date endDate = goods.getEndDate();
       Date nowDate = new Date();
       //秒杀状态
       int secKillStatus = 0;
       //剩余开始时间
       int remainSeconds = 0;
       //秒杀还未开始
       if (nowDate.before(startDate)) {
           remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
           // 秒杀已结束
       } else if (nowDate.after(endDate)) {
           secKillStatus = 2;
           remainSeconds = -1;
           // 秒杀中
       } else {
           secKillStatus = 1;
           remainSeconds = 0;
       }
       DetailVo detailVo = new DetailVo();
       detailVo.setGoodsVo(goods);
       detailVo.setUser(user);
       detailVo.setRemainSeconds(remainSeconds);
       detailVo.setSecKillStatus(secKillStatus);
       return RespBean.success(detailVo);
   }


}
