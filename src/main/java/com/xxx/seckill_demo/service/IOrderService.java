package com.xxx.seckill_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.seckill_demo.pojo.Order;
import com.xxx.seckill_demo.pojo.User;
import com.xxx.seckill_demo.vo.GoodsVo;
import com.xxx.seckill_demo.vo.OrderDetailVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yiyinghong
 * @since 2021-09-10
 */
public interface IOrderService extends IService<Order> {


    Order seckill(User user, GoodsVo goods);

    OrderDetailVo detail(Long orderId);

    String createPath(User user, Long goodsId);

    boolean checkPath(User user, Long goodsId, String path);
}
