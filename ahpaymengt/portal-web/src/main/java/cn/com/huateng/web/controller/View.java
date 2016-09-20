/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Desc: 二级域名入口 TODO 独立域名的处理
 * Author: dimzfw@gmail.com
 * Date: 8/16/12 10:48 AM
 */
@Controller
public class View {
    @Autowired
    private ViewRender viewRender;

    @RequestMapping(value = "/designer/{name}", method = RequestMethod.GET)
    public String designer(@RequestHeader("Host") String domain, HttpServletResponse response,
                           @PathVariable("name") String name, Map<String, Object> context) {
        context.put("_target_name", name);
        return viewRender.view(domain, "designer", null, response, context, true);
    }

    @RequestMapping(value = "/designer/{name}/stars", method = RequestMethod.GET)
    public String designerStars(@RequestHeader("Host") String domain, HttpServletResponse response,
                                @PathVariable("name") String name, Map<String, Object> context) {
        context.put("name", name);
        return viewRender.view(domain, "designer/stars", null, response, context, true);
    }

    /**
     * Item页面渲染入口
     *
     * @param itemId 商品ID
     */
    @RequestMapping(value = "/items/{itemId}", method = RequestMethod.GET)
    public String item(@RequestHeader("Host") String domain,
                       HttpServletRequest request, HttpServletResponse response,
                       @PathVariable("itemId") String itemId, Map<String, Object> context) {
        context.put("itemId", itemId);
        return viewRender.view(domain, "items", request, response, context, true);
    }

    @RequestMapping(value = "/shop/{sellerId}", method = RequestMethod.GET)
    public String shop(@RequestHeader("Host") String domain,
                       HttpServletRequest request, HttpServletResponse response,
                       @PathVariable("sellerId") String sellerId, Map<String, Object> context) {
        context.put("sellerId", sellerId);
        return viewRender.view(domain, "shop", request, response, context, true);
    }
}