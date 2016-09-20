/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.container;

import com.aixforce.common.utils.*;
import com.aixforce.site.handlebars.HandlebarEngine;
import com.aixforce.site.model.redis.Component;
import com.aixforce.site.model.redis.Widget;
import com.aixforce.site.service.ComponentService;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * Desc: 将一个WidgetList合并起来，有层次结构
 * Author: dimzfw@gmail.com
 * Date: 8/17/12 7:11 PM
 */
@org.springframework.stereotype.Component
public class BlockBuilder {
    private static Logger logger = LoggerFactory.getLogger(BlockBuilder.class);

    @Autowired
    private ComponentService componentService;

    @Autowired
    private HandlebarEngine handlebarEngine;

    private static Widget official_preview_bar = null;

    @PostConstruct
    public void initPreviewBarWidget() {
        Component component = componentService.findByPath("official/site/preview_bar");
        if (component == null) {
            logger.error("component not found for [official/site/preview_bar].");
            return;
        }
        official_preview_bar = new Widget();
        official_preview_bar.setCompId(component.getId());
    }

    public String compose(final Map<String, Object> context, Iterable<Widget> widgets, Map<String, String> uniqueComponents) {
        //1.generate widget tree
        TreeNode root = TreeBuilder.buildTree(widgets);
        return _compose(context, root, uniqueComponents);
    }

    public String composeOfficialPreviewBar(final Map<String, Object> context, Map<String, String> uniqueComponents) {
        return official_preview_bar == null ? "component not found for [official/site/preview_bar]" :
                _compose(context, new TreeNode(official_preview_bar), uniqueComponents);
    }

    private String _compose(final Map<String, Object> context, TreeNode root, final Map<String, String> uniqueComponents) {

        final Map<Long, StringBuilder> myChildren = Maps.newHashMap();
        final Map<Long, StringBuilder> myRowBoxChildren = Maps.newHashMap();

        //2.travel and merger
        String ret = TreeBuilder.travel(root, new NodeVisitor() {

            @Override
            public String visit(Node node) {
                Widget currWidget = (Widget) node;

                //不污染context
                Map<String, Object> dataMap = new HashMap<String, Object>();
                // id == null means its a item desc mock widget
                if (currWidget.getId() == -1L) {
                    return myChildren.remove(-1L).toString();
                }
                Component component = componentService.findById(currWidget.getCompId());
                if (component == null) {
                    return null;
                }
                //record all components ,path & name
                uniqueComponents.put(component.getPath() + ":" + component.getName(), null);

                dataMap.put(RenderConstants.COMPONENT_NAME, component.getName());

                dataMap.put(RenderConstants.BEHAVIOR, currWidget.getBehavior());

                //css class
                dataMap.put(RenderConstants.MODE, currWidget.getMode());

                //config params
                @SuppressWarnings("unchecked")
                Map<String, Object> configs = JsonMapper.nonEmptyMapper().fromJson(currWidget.getJsonData(), Map.class);
                if (configs != null) {
                    dataMap.putAll(configs);
                }
                //request params
                if (context != null) {
                    dataMap.putAll(context);
                }

                //children 如果是通栏，要放到特定的位置上，参考page_header,page_footer,page_body三个组件的dom结构
                dataMap.put(RenderConstants.ROW_BOX_CHILDREN, myRowBoxChildren.remove(currWidget.getId()));
                dataMap.put(RenderConstants.CHILDREN, myChildren.remove(currWidget.getId()));

                //execute handlebar template , call service in this method
                String data = handlebarEngine.execComponent(currWidget, component, dataMap);

                //增加对row_box的判断
                if (RenderConstants.ROW_BOX_COMP_PATH.equals(component.getPath())) {
                    if (myRowBoxChildren.containsKey(currWidget.getParentId())) {
                        myRowBoxChildren.get(currWidget.getParentId()).append(data);
                    } else {
                        myRowBoxChildren.put(currWidget.getParentId(), new StringBuilder(data));
                    }
                } else {
                    if (myChildren.containsKey(currWidget.getParentId())) {
                        myChildren.get(currWidget.getParentId()).append(data);
                    } else {
                        myChildren.put(currWidget.getParentId(), new StringBuilder(data));
                    }
                }
                return data;
            }
        });

        logger.debug("build a block root's id:{}", root.current.getId());

        return ret;
    }
}