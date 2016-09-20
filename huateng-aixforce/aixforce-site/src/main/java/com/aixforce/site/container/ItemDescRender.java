/*
 * Copyright (c) 2013 杭州端点网络科技有限公司
 */

package com.aixforce.site.container;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.site.model.redis.Widget;
import com.aixforce.site.service.WidgetService;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Desc: 商品详情渲染服务，给item/desc组件调用的
 * Author: dimzfw@gmail.com
 * Date: 3/12/13 5:00 PM
 */
@Service
public class ItemDescRender {

    @Autowired
    private BlockBuilder blockBuilder;

    @Autowired
    private WidgetService widgetService;

    public String render(@ParamInfo("itemId") Long itemId) {
        Map<String, String> uniqueComponents = Maps.newHashMap();

        Map<String, Object> context = Maps.newHashMap();

        List<Widget> descWidgets = widgetService.itemDescWidgets(itemId);
        if (descWidgets.size() > 0) {
            // generate json first, cause descWidgets will be modified next
            String pageDataJson = PageRender.genPageDataJson(descWidgets);
            // transform all null parentId to -1L, means itemDescRoot
            for (Widget widget : descWidgets) {
                if (widget.getParentId() == null) {
                    widget.setParentId(-1L);
                }
            }
            // add mock itemDescRootWidget
            Widget itemDescRootWidget = new Widget();
            itemDescRootWidget.setId(-1L);
            descWidgets.add(itemDescRootWidget);
            //description's uniqueComponents & descData
            String ret = blockBuilder.compose(context, descWidgets, uniqueComponents);
            ret += "<input id=\"uniqueDescComponents\" type=\"hidden\" value=\"" + Joiner.on(",").join(uniqueComponents.keySet()).replace("/", "_") + "\">";
            ret += "<input id=\"descData\" type=\"hidden\" value='" + pageDataJson + "'>";

            return ret;
        } else {
            return "";
        }
    }
}
