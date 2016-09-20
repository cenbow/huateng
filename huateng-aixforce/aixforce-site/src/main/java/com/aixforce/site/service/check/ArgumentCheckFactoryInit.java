/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service.check;

import com.aixforce.common.check.ArgumentCheckFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 11/22/12 1:32 PM
 */
@Service
public class ArgumentCheckFactoryInit {

    @Autowired
    private ArgumentCheckFactory argumentCheckFactory;

    @Autowired
    SiteChecker siteChecker;

    @PostConstruct
    public void init() {
        argumentCheckFactory.addChecker("site", siteChecker);
    }
}
