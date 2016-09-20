/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service.check;

import com.aixforce.common.check.Checker;
import com.aixforce.site.dao.redis.RedisSiteDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 11/22/12 1:07 PM
 */
@Service
public class SiteChecker implements Checker {
    @Autowired
    private RedisSiteDao siteDao;

    @Override
    public boolean check(String attrName, String value) {
        if ("domain".equals(attrName)) {
            return siteDao.findByDomain(value, false) == null;
        }
        if ("subDomain".equals(attrName)) {
            return siteDao.findByDomain(value, true) == null;
        }
        return true;
    }
}