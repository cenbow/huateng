package com.aixforce.site.container.executor;

/**
 * Desc:  对调用结果进行额外处理的接口
 * Author: dimzfw@gmail.com
 * Date: 7/25/13 3:57 PM
 */
public interface ResultResolver {
    Object resolver(Object object);
}
