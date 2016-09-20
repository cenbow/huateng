/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 11/10/12 5:31 PM
 */
public interface SiteForkService {

    /**
     * 根据siteId fork 一个站点
     *
     * @param userId   当前操作用户
     * @param siteId 被fork的站点的id，名字和official_preview_bar的hbs里指定的相同
     * @return new site id
     */
    Long fork(Long userId, Long siteId);

}