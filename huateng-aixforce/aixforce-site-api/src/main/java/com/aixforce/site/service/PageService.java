/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.SiteInstance;

import java.util.List;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/16/12 5:13 PM
 */
public interface PageService {

    void createOrUpdate(Page page);

    Page findById(Long id);

    void delete(Long id);

    List<Page> findBySiteInstanceId(Long siteInstanceId);

    List<Page> findBySiteInstance(@ParamInfo("siteInstance")SiteInstance siteInstance);

    Page findBySiteInstanceAndPath(Long siteInstanceId, String path);

}
