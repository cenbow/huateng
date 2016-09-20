/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.model.redis.SiteCategory;

import java.util.List;
import java.util.Map;

/*
* Author: jlchen
* Date: 2012-11-28
*/
public interface SiteCategoryService {
    SiteCategory findById(Long id);

    void update(SiteCategory siteCategory);

    void delete(Long id);

    Long create(SiteCategory siteCategory);

    //Map<String,List<SiteCategory>> fromCache();

    Map<String, List<SiteCategory>> group(Iterable<SiteCategory> siteCategories);

    List<SiteCategory> all();
}
