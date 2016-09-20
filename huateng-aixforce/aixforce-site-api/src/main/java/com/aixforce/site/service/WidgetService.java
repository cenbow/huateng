/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.model.redis.Widget;

import java.util.List;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/17/12 1:34 PM
 */
public interface WidgetService {

    /**
     * find widget by id
     */
    Widget findById(Long id);

    /**
     * get page's widgets by page id
     *
     * @param pageId page's id
     * @return page's widgets
     */
    List<Widget> pageLevelWidget(Long pageId);

    /**
     * get site level widgets by site instance id
     *
     * @param belongType     header or footer
     * @param siteInstanceId site instance id
     * @return head or footer's widgets
     */
    List<Widget> siteLevelWidgets(Widget.BelongType belongType, Long siteInstanceId);

    /**
     * get item describe widgets  by item id
     *
     * @param itemId item id
     * @return description widgets of item
     */
    List<Widget> itemDescWidgets(Long itemId);

    /**
     * 1.create root widget by component name for siteInstance page and whole site
     *
     * @param belongId   site instance's id or page's id or whole site's id
     * @param belongType header footer page or site
     * @return widget
     */
    Widget createRootWidget(Long belongId, Widget.BelongType belongType);

    /**
     * 2.create new widget and add it into page.
     *
     * @param pageWidgetId page's widget id which component added into.
     * @param compId       new component's id
     * @return widget
     */
    Widget addNewWidget(Long pageWidgetId, Long compId);

    /**
     * 2[+].create new item widget and add it into page.
     *
     * @param itemId
     * @param compId
     * @return
     */
    Widget addItemWidget(Long itemId, Long compId);

    /**
     * 3.modify widget's params
     *
     * @param id       widget's id
     * @param jsonData json format data for widget
     */
    void updateParams(Long id, String jsonData);

    /**
     * 4.modify widget's behavior config
     *
     * @param id       widget's id
     * @param behavior widget's behavior
     */
    void updateBehavior(Long id, String behavior);

    /**
     * 5.delete widget by id s
     *
     * @param widgetId delete target widget id
     * @param children all children of target
     */
    void delete(Long widgetId, List<Long> children);

    /**
     * 6.update page structure
     *
     * @param siteInstanceId site instance id
     * @param pageId         page id
     * @param structureJson  page's widgets' structure data ,include position and size in style field, and self's id and parent widget's id
     */
    void savePageStructure(Long siteInstanceId, Long pageId, String structureJson);

    /**
     * 7.clone widget by id ,cloned with the same parent of source.
     *
     * @param widgetId target widget id
     * @param children all children of target
     * @return cloned widget with all widget's component path in mode field.
     */
    Widget clone(Long widgetId, List<Long> children);

    /**
     * 保存商品描述信息
     *
     * @param itemId        商品id
     * @param structureJson 商品描述的widget
     */
    void saveItemDescStructure(Long itemId, String structureJson);
}
