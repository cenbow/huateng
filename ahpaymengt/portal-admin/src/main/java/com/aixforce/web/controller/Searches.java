/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.controller;

import com.aixforce.web.jobs.ItemSearchDumper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-10
 */
@Controller
@RequestMapping("/api/search")
public class Searches {
    @Autowired
    private ItemSearchDumper itemSearchDumper;

    @RequestMapping(value = "/items/delta", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    //@RequiresPermissions("search:update")
    public String deltaDumpItems() {
        itemSearchDumper.deltaDumpItem();
        return "delta dump item success";
    }

    @RequestMapping(value = "/items/full", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
   // @RequiresPermissions("search:update")
    public String fullDumpItems() {
        itemSearchDumper.fullDump();
        return "full dump item success";
    }
}
