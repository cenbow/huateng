/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller;

import com.aixforce.common.check.ArgumentCheckFactory;
import com.aixforce.site.container.BlockBuilder;
import com.aixforce.site.container.RenderConstants;
import com.aixforce.site.container.executor.SpringServiceExecutor;
import com.aixforce.site.model.redis.Component;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.model.redis.Widget;
import com.aixforce.site.service.ComponentService;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 11/8/12 9:34 PM
 */
@Controller
public class Service {
    private final static Logger log = LoggerFactory.getLogger(Service.class);

    @Autowired
    private ComponentService componentService;

    @Autowired
    private SpringServiceExecutor springServiceExecutor;

    @Autowired
    private BlockBuilder blockBuilder;

    @Autowired
    private ArgumentCheckFactory argumentCheckFactory;


    @RequestMapping(value = "/api/common/{componentId}/{method}")
    @ResponseBody
    public Object commonCall(@PathVariable Long componentId, @PathVariable String method, HttpServletRequest request, HttpServletResponse response, Map<String, Object> context) {
        for (Object name : request.getParameterMap().keySet()) {
            context.put((String) name, request.getParameter((String) name));
        }
        checkNotNull(componentId);
        checkNotNull(method);
        Component component = componentService.findById(componentId);
        checkNotNull(component);
        if (component.getApis() == null || component.getApis().get(method) == null) {
            return null;
        }

        try {
            Object ret = springServiceExecutor.exec(component.getApis().get(method), context);
            String redirect_url = (String) context.get("redirect_url");
            if (!Strings.isNullOrEmpty(redirect_url)) {
                response.sendRedirect(redirect_url);
                return null;
            }

            return ret;

        } catch (Exception e) {
            log.error("execute service failed! service(component={}", componentId + ":" + method, e);
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    @RequestMapping(value = "/api/template/{templatePath}/{siteId}")
    @ResponseBody
    public Object template(@PathVariable String templatePath,@PathVariable Long siteId) {
        Component component = componentService.findByPath(templatePath.replace('-','/'));
        Widget widget = new Widget();
        widget.setId(-1L);
        widget.setCompId(component.getId());
        List<Widget> widgetList = Lists.newArrayList();
        widgetList.add(widget);
        Map<String, Object> context = Maps.newHashMap();
        Site site = new Site();
        site.setId(siteId);
        context.put(RenderConstants.SITE, site);

        return blockBuilder.compose(context, widgetList, new HashMap<String,String>());
    }

    @RequestMapping(value = "/api/check/{objectName}/{attrName}/{value}/", method = RequestMethod.POST)
    @ResponseBody
    public Object checkArgument(@PathVariable String objectName, @PathVariable String attrName, @PathVariable String value) {
        return argumentCheckFactory.getChecker(objectName).check(attrName, value);
    }
}