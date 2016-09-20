/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.web.controller;

import com.aixforce.category.dto.RichCategory;
import com.aixforce.category.model.Category;
import com.aixforce.category.service.CategoryService;
import com.aixforce.exception.JsonResponseException;
import com.aixforce.item.model.Spu;
import com.aixforce.item.service.SpuService;
import com.aixforce.web.misc.MessageSources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-31
 */
@Controller
@RequestMapping("/api/categories")
public class Categories {

    private final static Logger log = LoggerFactory.getLogger(Categories.class);

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SpuService spuService;

    @Autowired
    private MessageSources messageSources;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RichCategory> list() {
        try {
            return categoryService.childrenOf(0L);
        } catch (Exception e) {
            log.error("failed to load root categories,cause:{}",e);
            throw new JsonResponseException(500, messageSources.get("category.fail.load"));
        }
    }

    @RequestMapping(value = "/{id}/children", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<RichCategory> childrenOf(@PathVariable("id") Long categoryId) {
        try {
            return categoryService.childrenOf(categoryId);
        } catch (Exception e) {
            log.error("failed to load sub categories of {},cause:{}",categoryId,e);
            throw new JsonResponseException(500, messageSources.get("category.query.fail"));
        }
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Category newCategory(@Valid Category category,final BindingResult result) {
        if(result.hasErrors()){
            throw new JsonResponseException(500,result.getFieldError().getDefaultMessage());
        }
        try {
            category.setType(Category.Type.BACKEND.toNumber());
            categoryService.create(category);
            return category;
        } catch (IllegalArgumentException e) {
            return logException(category, 400,e);
        } catch (IllegalStateException e) {
            return logException(category,500,e);
        } catch (Exception e){
            return logException(category,500,e);
        }
    }


    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String removeChild(@PathVariable("id") Long id){
        try {
            categoryService.delete(id);
            return messageSources.get("category.delete.success");
        } catch (Exception e) {
            log.error("failed to delete category {},cause:{}",id,e);
            throw new JsonResponseException(500, messageSources.get("category.delete.fail"));
        }
    }


    @RequestMapping(value="/{categoryId}/spus",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Spu> findByCategoryId(@PathVariable("categoryId")Long categoryId){
        try {
            return spuService.findByCategoryId(categoryId);
        } catch (Exception e) {
            log.error("failed to find Spus for categoryId {},cause:{}",categoryId,e);
            throw new JsonResponseException(500,messageSources.get("spu.query.fail"));
        }
    }


    private Category logException(Category category, int status,Exception e) {
        log.error("failed to create category :{}, cause:{}", category, e);
        throw new JsonResponseException(status, messageSources.get("category.create.fail"));
    }
}
