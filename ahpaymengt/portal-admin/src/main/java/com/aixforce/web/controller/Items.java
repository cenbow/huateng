/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.controller;

import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.model.Item;
import com.aixforce.item.service.ItemService;
import com.aixforce.web.misc.MessageSources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;
import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-07
 */
@Controller
@RequestMapping("/api/items")
public class Items {
    private final static Logger log = LoggerFactory.getLogger(Items.class);

    @Autowired
    private  ItemService itemService;

    @Autowired
    private MessageSources messageResources;

    @RequestMapping(method = RequestMethod.GET)
    public List<Item> search(){
        return Collections.emptyList();
    }

    public String update(Item item){
        try {
            itemService.update(item,null,null);
            return messageResources.get("item.update.success");
        } catch (IllegalArgumentException e) {
            log.error("failed to update item to {},cause:{}",item,e);
            throw new JsonResponseException(400,messageResources.get("item.update.fail"));
        }
    }
}
