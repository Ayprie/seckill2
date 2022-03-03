package com.xxx.seckill_demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.seckill_demo.pojo.Goods;
import com.xxx.seckill_demo.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yiyinghong
 * @since 2021-09-10
 */
public interface IGoodsService extends IService<Goods> {
    /**
     * 功能描述，获取商品详情
     * @return
     */

    List<GoodsVo> findGoodsVo();

   GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
