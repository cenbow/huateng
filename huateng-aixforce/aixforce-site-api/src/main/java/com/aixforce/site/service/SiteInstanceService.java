/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.model.redis.SiteInstance;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/17/12 6:29 PM
 */
public interface SiteInstanceService {

    /**
     * 创建site Instance
     *
     * @param siteInstance 被创建的siteInstance
     * @param withPagePart 是否同步创建头尾和默认页面，全新创建站点的时候是true，fork站点是false
     * @return siteInstance's id
     */
    Long create(SiteInstance siteInstance, boolean withPagePart);

    SiteInstance findById(Long id);

    void update(SiteInstance siteInstance);
}
