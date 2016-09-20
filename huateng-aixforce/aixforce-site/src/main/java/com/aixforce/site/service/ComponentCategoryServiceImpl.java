/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.dao.redis.RedisComponentCategoryDao;
import com.aixforce.site.model.redis.ComponentCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 9/1/12 5:30 PM
 */
@Service("componentCategoryService")
public class ComponentCategoryServiceImpl implements ComponentCategoryService {
    @Autowired
    private RedisComponentCategoryDao componentCategoryDao;

    @Override
    public List<ComponentCategory> findByParentId(Long parentId) {
        return componentCategoryDao.findByParentId(parentId);
    }
}
