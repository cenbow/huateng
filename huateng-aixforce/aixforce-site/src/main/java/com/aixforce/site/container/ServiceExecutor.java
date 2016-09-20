/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.container;

import java.util.Map;

/**
 * Desc: 执行组件的服务
 * Author: dimzfw@gmail.com
 * Date: 8/17/12 2:15 PM
 */
public interface ServiceExecutor {
    /**
     * 执行组件的服务
     *
     * @param api    服务API
     * @param params 页面组件实例
     * @return 组件执行结果的html代码
     */
    public Object exec(String api, Map<String, Object> params);
}