/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.model.redis.ComponentCategory;

import java.util.List;

/**
 * Author: dimzfw@gmail.com
 * Date: 9/1/12 5:29 PM
 */
public interface ComponentCategoryService {

    List<ComponentCategory> findByParentId(Long parentId);

}
