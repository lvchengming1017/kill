package com.debug.kill.server.service;/*
 *FileName:  IKillService
 * Author:   lvchengming
 * Date  :   2020/2/17 20:57
 * */

public interface IKillService {

    Boolean killItem(Integer killId,Integer userId) throws Exception;

    Boolean killItemV2(Integer killId, Integer userId) throws Exception;

    Boolean killItemV3(Integer killId, Integer userId) throws Exception;

    Boolean killItemV4(Integer killId, Integer userId) throws Exception;

    Boolean killItemV5(Integer killId, Integer userId) throws Exception;
}
