/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 12/13/12 11:30 AM
 */
@Component
public final class CommonConstants {

    public static final String SESSION_USER_ID ="session_user_id";

    public static final String COOKIE_SHOP_CART = "cart";

    public final static String PREVIOUS_URL = "previous_url";

    private final String mainSite;
    private final String domain;

    @Autowired
    public CommonConstants(@Value("#{app.domain}")String domain,@Value("#{app.mainSite}")String mainSite) {
        this.mainSite = mainSite;
        this.domain = domain;
    }

    public String getMainSite() {
        return mainSite;
    }

    public String getDomain() {
        return domain;
    }
}