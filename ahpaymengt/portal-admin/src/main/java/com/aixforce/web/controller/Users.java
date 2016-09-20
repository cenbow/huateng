/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.controller;

import com.aixforce.site.service.ImageService;
import com.aixforce.site.service.QuotaService;
import com.aixforce.user.base.UserUtil;
import com.aixforce.user.model.User;
import com.aixforce.user.service.AccountService;
import com.aixforce.user.service.UserProfileService;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-18
 */
@Controller
@RequestMapping("/users")
public class Users {
    @Autowired
    private AccountService accountService;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private QuotaService quotaService;

    @Autowired
    private ImageService imageService;


    @RequestMapping(method = RequestMethod.GET)
    public String index(@RequestParam(value = "q", defaultValue = "") String keywords,
                        @RequestParam(value = "queryType", defaultValue = "byName") String queryType,
                        @RequestParam(value = "p", defaultValue = "1") Integer pageNo,
                        @RequestParam(value = "status", defaultValue = "1") Integer status,
                        Map<String, Object> context) {
        if (!Strings.isNullOrEmpty(keywords)) {
            User user;
            if (Objects.equal(queryType, "byName")) {
                user = accountService.findUserByName(keywords);

            } else {
                user = accountService.findUserById(Long.parseLong(keywords));

            }
            if(user == null){
                context.put("users", Collections.emptyList());
            }else{
                context.put("users", ImmutableList.of(user));
            }

        } else {
            context.put("users", accountService.list(status, pageNo, 20).getData());
        }
        context.put("keywords", keywords);
        context.put("userName", UserUtil.getCurrentUser().getPrincipal());
        return "admin/users/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String create(User user) {
        accountService.createUser(user);

        return "redirect:/users";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String update(@PathVariable("id") Long id, User user) {
        user.setId(id);
        accountService.updateUser(user);
        return "redirect:/users/" + user.getId();
    }

    @RequestMapping("/new")
    public String newer(Map<String, Object> context) {
        context.put("userName", UserUtil.getCurrentUser().getPrincipal());
        return "admin/users/new";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(@PathVariable("id") Long id, Map<String, Object> map) {
        User user = accountService.findUserById(id);
        if (user == null) {
            throw new IllegalStateException("can not found user with id=" + id);
        }
        map.put("user", user);
        map.put("userName", UserUtil.getCurrentUser().getPrincipal());
        return "admin/users/show";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        User user = accountService.findUserById(id);
        if(user != null){
            accountService.deleteUser(id);

        }
        quotaService.deleteByUserId(id);
        imageService.deleteByUserId(id);
        return "redirect:/users";
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Map<String, Object> map) {
        User user = accountService.findUserById(id);
        if (user == null) {
            throw new IllegalStateException("can not found user with id=" + id);
        }
        map.put("user", user);
        map.put("userProfile",userProfileService.findUserProfileByUserId(id));
        map.put("userName", UserUtil.getCurrentUser().getPrincipal());
        return "admin/users/edit";
    }
}
