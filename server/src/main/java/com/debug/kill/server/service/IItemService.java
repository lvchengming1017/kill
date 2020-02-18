package com.debug.kill.server.service;/*
 *FileName:  IItemService
 * Author:   lvchengming
 * Description: TODO
 * Date  :   2020/2/17 10:23
 * Version: 1.0
 * */

import com.debug.kill.model.entity.ItemKill;

import java.util.List;

public interface IItemService {

    List<ItemKill> getKillItems() throws Exception;

    ItemKill getKillDetail(Integer id) throws Exception;
}
