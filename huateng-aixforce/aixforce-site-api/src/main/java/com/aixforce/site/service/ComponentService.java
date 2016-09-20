/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.model.redis.Component;

import java.util.List;

/**
 * Desc:组件相关服务，主要是管理的功能
 * Author: dimzfw@gmail.com
 * Date: 8/15/12 6:28 PM
 */
public interface ComponentService {

    /**
     * 根据ID获取组件原型，需要被cache，页面渲染的时候用到
     *
     * @param compId 唯一标识，数字ID
     * @return Component对象
     */
    Component findById(Long compId);

    /**
     * 按分类Id查询
     *
     * @param categoryId component category's id
     * @return component list in this category
     */
    List<Component> findByCategoryId(Long categoryId);


    /**
     * 只有2个地方使用
     */
    Component findByPath(String path);

    /**
     * 添加组件原型
     *
     * @param comp component
     */
    void create(Component comp);

    /**
     * 删除组件原型
     *
     * @param compId 唯一标识，数字ID
     */
    void delete(Long compId);

    /**
     * 更新组件原型
     *
     * @param comp component
     */
    void update(Component comp);


    /**
     * 列出所有的组件
     * @return 查询结果
     */
    List<Component> all();
}
