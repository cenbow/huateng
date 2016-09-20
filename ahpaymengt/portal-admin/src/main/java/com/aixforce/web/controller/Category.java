package com.aixforce.web.controller;

import com.aixforce.user.base.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * User: saito
 * Date: 5/8/13
 * Time: 5:35 PM
 */
@Controller
@RequestMapping("/categories")
public class Category {

    @RequestMapping(method = RequestMethod.GET)
    public String categories(Map<String,String> context){
        context.put("userName", UserUtil.getCurrentUser().getPrincipal());
        return "admin/categories/index";
    }
}
