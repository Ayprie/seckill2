package com.xxx.seckill_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.seckill_demo.pojo.SeckillOrder;
import com.xxx.seckill_demo.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yiyinghong
 * @since 2021-09-10
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    Long getResult(User user, Long goodsId);
}
