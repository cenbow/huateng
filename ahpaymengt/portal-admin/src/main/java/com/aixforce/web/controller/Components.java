/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.controller;

import com.aixforce.common.utils.JsonMapper;
import com.aixforce.site.model.redis.Component;
import com.aixforce.site.service.ComponentService;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/25/12 4:02 PM
 */
@Controller
@RequestMapping("/components")
public class Components {
    private final Logger log = LoggerFactory.getLogger(Components.class);
    @Autowired
    private ComponentService componentService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Map<String, Object> map) {
        List<Component> result = componentService.all();
        System.out.println("componet list size:" + result.size());
        map.put("components", result);
        map.put("userName", UserUtil.getCurrentUser().getPrincipal());
        return "admin/components/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(Component component) {
        if (!Strings.isNullOrEmpty(component.getApi())) {
            JsonMapper.nonDefaultMapper().fromJson(component.getApi(),
                    JsonMapper.nonDefaultMapper().createCollectionType(Map.class, String.class, String.class));
        }
        componentService.create(component);
        return "redirect:/components";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") Long id, Component component) {
        component.setId(id);
        /*Map<String,String> apis = Maps.newHashMap();
        for (String name : request.getParameterMap().keySet()) {
           if(name.startsWith("api-key-")){
               String originName = name.substring(8);
               String realName = request.getParameter(name);
               String newValue = request.getParameter("api-value-"+originName);
               if(!Strings.isNullOrEmpty(newValue)){
                   apis.put(realName,newValue);
               }
           }
        }*/
        if (Strings.isNullOrEmpty(component.getApi())) {
            component.setApi("");
        } else { //try parse to validate json
            JsonMapper.nonDefaultMapper().fromJson(component.getApi(),
                    JsonMapper.nonDefaultMapper().createCollectionType(Map.class, String.class, String.class));
        }
        componentService.update(component);

        return "redirect:/components/" + component.getId();
    }

    @RequestMapping("/new")
    public String newer(Map<String, Object> context) {
        context.put("userName", UserUtil.getCurrentUser().getPrincipal());
        return "admin/components/new";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Map<String, Object> map) {
        Component component = componentService.findById(id);
        if (component == null) {
            log.warn("can not found component with id={}", id);
            throw new IllegalStateException("can not found component with id=" + id);
        }
        if (component.getApis() != null && !component.getApis().isEmpty()) {
            component.setApi(JsonMapper.nonDefaultMapper().toJson(component.getApis()));
        }
        map.put("component", component);
        map.put("userName", UserUtil.getCurrentUser().getPrincipal());
        return "admin/components/show";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String delete(@PathVariable("id") Long id) {
        componentService.delete(id);
        return "delete ok";
    }

    @RequestMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Map<String, Object> map) {
        Component component = componentService.findById(id);
        if (component == null) {
            log.warn("can not found component with id={}", id);
            throw new IllegalStateException("can not found component with id=" + id);
        }
        if (component.getApis() != null && !component.getApis().isEmpty()) {
            component.setApi(JsonMapper.nonDefaultMapper().toJson(component.getApis()));
        }
        map.put("userName", UserUtil.getCurrentUser().getPrincipal());
        map.put("component", component);
        /*if(component.getApis()==null){
            map.put("apis",Collections.emptySet());
        } else{
            map.put("apis",component.getApis().entrySet());
        }*/
        return "admin/components/edit";
    }
}
