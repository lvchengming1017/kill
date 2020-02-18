package com.debug.kill.server.service.impl;/*
 *FileName:  IKillServiceImpl
 * Author:   lvchengming
 * Description: TODO
 * Date  :   2020/2/17 20:58
 * Version: 1.0
 * */

import com.debug.kill.model.entity.ItemKill;
import com.debug.kill.model.entity.ItemKillSuccess;
import com.debug.kill.model.mapper.ItemKillMapper;
import com.debug.kill.model.mapper.ItemKillSuccessMapper;
import com.debug.kill.server.enums.SysConstant;
import com.debug.kill.server.service.IKillService;
import com.debug.kill.server.service.RabbitSenderService;
import com.debug.kill.server.utils.SnowFlake;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KillServiceImpl implements IKillService {
    private static final Logger log= LoggerFactory.getLogger(KillServiceImpl.class);

    private SnowFlake snowFlake=new SnowFlake(2,3);

    @Autowired
    private ItemKillMapper itemKillMapper;

    @Autowired
    private ItemKillSuccessMapper itemKillSuccessMapper;

    @Autowired
    private RabbitSenderService rabbitSenderService;
    @Override
    public Boolean killItem(Integer killId, Integer userId) throws Exception {
       Boolean result =false;
       //TODO:判断当前用户是否已经抢购过当前商品
       if (itemKillSuccessMapper.countByKillUserId(killId,userId) <=0){
           ItemKill itemKill =itemKillMapper.selectById(killId);
           if (itemKill != null && 1==itemKill.getCanKill()) {
               int res=itemKillMapper.updateKillItem(killId);
         if (res>0){
             System.out.println(res+"123");
            commonRecordKillSuccessInfo(itemKill,userId);
        result = true;
         }
           }
       }else {
           throw  new Exception("你已经抢购过了");
       }

        return result;
    }


    @Override
    public Boolean killItemV2(Integer killId, Integer userId) throws Exception {
        return null;
    }

    @Override
    public Boolean killItemV3(Integer killId, Integer userId) throws Exception {
        return null;
    }

    @Override
    public Boolean killItemV4(Integer killId, Integer userId) throws Exception {
        return null;
    }

    @Override
    public Boolean killItemV5(Integer killId, Integer userId) throws Exception {
        return null;
    }

    /**
     * 通用的方法-记录用户秒杀成功后生成的订单-并进行异步邮件消息的通知
     * @param kill
     * @param userId
     * @throws Exception
     */
    private void commonRecordKillSuccessInfo(ItemKill kill,Integer userId) throws Exception{
        //TODO:记录抢购成功后生成的秒杀订单记录

        ItemKillSuccess entity=new ItemKillSuccess();
        String orderNo=String.valueOf(snowFlake.nextId());

        //entity.setCode(RandomUtil.generateOrderCode());   //传统时间戳+N位随机数
        entity.setCode(orderNo); //雪花算法
        entity.setItemId(kill.getItemId());
        entity.setKillId(kill.getId());
        entity.setUserId(userId.toString());
        entity.setStatus(SysConstant.OrderStatus.SuccessNotPayed.getCode().byteValue());
        entity.setCreateTime(DateTime.now().toDate());
        //TODO:学以致用，举一反三 -> 仿照单例模式的双重检验锁写法
        if (itemKillSuccessMapper.countByKillUserId(kill.getId(),userId) <= 0){
            int res=itemKillSuccessMapper.insertSelective(entity);

            if (res>0){
                //TODO:进行异步邮件消息的通知=rabbitmq+mail
                rabbitSenderService.sendKillSuccessEmailMsg(orderNo);

                //TODO:入死信队列，用于 “失效” 超过指定的TTL时间时仍然未支付的订单
                rabbitSenderService.sendKillSuccessOrderExpireMsg(orderNo);
            }
        }
    }
}
