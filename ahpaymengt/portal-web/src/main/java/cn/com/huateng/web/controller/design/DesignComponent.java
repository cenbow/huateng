/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller.design;

import com.aixforce.site.model.redis.Component;
import com.aixforce.site.model.redis.ComponentCategory;
import com.aixforce.site.service.ComponentCategoryService;
import com.aixforce.site.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 9/1/12 10:35 AM
 */
@Controller
@RequestMapping("/design/component")
public class DesignComponent {

    @Autowired
    private ComponentCategoryService componentCategoryService;

    @Autowired
    private ComponentService componentService;

    /**
     * List categories
     *
     * @param parentId category's parentId
     * @return component object list to json
     */
    @RequestMapping(value = "category/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public List<ComponentCategory> listComponentCategory(@PathVariable("parentId") long parentId) {
        return componentCategoryService.findByParentId(parentId);
    }

    /**
     * list components
     *
     * @param componentCategoryId category id
     * @return component list in this category
     */
    @RequestMapping(value = "list/{componentCategoryId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Component> listComponent(@PathVariable("componentCategoryId") long componentCategoryId) {
        return componentService.findByCategoryId(componentCategoryId);
    }
}
