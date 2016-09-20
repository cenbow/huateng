/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.container;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Desc:  执行器选择
 * Author: dimzfw@gmail.com
 * Date: 8/17/12 7:48 PM
 */
@org.springframework.stereotype.Component
public class ExecutorFactory {
    @Autowired
    private ServiceExecutor springServiceExecutor;

    public ServiceExecutor getExecutor() {
        return this.springServiceExecutor;
    }
}
