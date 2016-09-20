/*
 * Copyright (c) 2013 杭州端点网络科技有限公司
 */

package com.aixforce.user.base;

import java.io.Serializable;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-03-16
 */
public class InnerCookie implements Serializable {
    private static final long serialVersionUID = -5440120009211709421L;

    private final Map<String,String> cookies;

    public InnerCookie(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String get(String name){
        return cookies.get(name);
    }
}
