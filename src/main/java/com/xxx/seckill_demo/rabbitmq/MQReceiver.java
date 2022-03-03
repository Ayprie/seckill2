package com.xxx.seckill_demo.rabbitmq;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.seckill_demo.pojo.SeckillMessage;
import com.xxx.seckill_demo.pojo.SeckillOrder;
import com.xxx.seckill_demo.pojo.User;
import com.xxx.seckill_demo.service.IGoodsService;
import com.xxx.seckill_demo.service.IOrderService;
import com.xxx.seckill_demo.utils.JsonUtil;
import com.xxx.seckill_demo.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j


public class MQReceiver {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = "seckillQueue")
    public void receive(String msg) {
        log.info("QUEUE接受消息：" + msg);
        SeckillMessage message = JsonUtil.jsonStr2Object(msg, SeckillMessage.class);
        Long goodsId = message.getGoodsId();
        User user = message.getUser();

        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount() < 1) {
            return;
        }
        //判断是否重复抢购
        // SeckillOrder seckillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder> ().eq("user_id",
                //       user.getId()).eq(
                //       "goods_id",
                //       goodsId));
        String seckillOrderJson = (String) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodsId);
        if (!StringUtils.isEmpty(seckillOrderJson)) {
            return;
        }
        //下单
        orderService.seckill(user, goods);
    }







//    @RabbitListener(queues = "queue")
//    public void receive(Object msg){
//        log.info("接受消息："+msg);
//    }
//
//    @RabbitListener(queues = "queue_fanout01")
//    public void receive01(Object msg){
//        log.info("queue01接受消息："+msg);
//    }
//
//    @RabbitListener(queues = "queue_fanout02")
//    public void revceive02(Object msg){
//        log.info("queue02接受消息："+msg);
//    }
//
//    @RabbitListener(queues = "queue_direct01")
//    public void receiveDirect01(Object msg){
//        log.info("queue01 red "+msg);
//    }
//    @RabbitListener(queues = "queue_direct02")
//    public void receiveDirect02(Object msg){
//        log.info("queue02 green "+msg);
//    }
//
//
//    @RabbitListener(queues ="queue_topic01")
//    public void receiveDirect03(Object msg){
//        log.info("queue01--topic  "+msg);
//    }
//
//
//    @RabbitListener(queues = "queue_topic02")
//    public void receiveDirect04(Object msg){
//        log.info("queue02——topic   "+msg);
//    }
//
//
//    @RabbitListener(queues = "queue_header01")
//    public void receive01_header(Message message) {
//        log.info("QUEUE01接受Message对象：" + message);
//        log.info("QUEUE01接受消息：" + new String(message.getBody()));
//    }
//    @RabbitListener(queues = "queue_header02")
//    public void receive02_header(Message message) {
//        log.info("QUEUE02接受Message对象：" + message);
//        log.info("QUEUE02接受消息：" + new String(message.getBody()));
//    }
}

