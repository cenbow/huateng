/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.common.utils.*;
import com.aixforce.site.dao.redis.RedisWidgetDao;
import com.aixforce.site.model.redis.Component;
import com.aixforce.site.model.redis.Widget;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * Desc: widget crud move , style and params manage
 * Author: dimzfw@gmail.com
 * Date: 8/19/12 4:56 PM
 */
@Service("widgetService")
public class WidgetServiceImpl implements WidgetService {
    private static Logger logger = LoggerFactory.getLogger(WidgetServiceImpl.class);

    @Autowired
    private ComponentService componentService;

    @Autowired
    private RedisWidgetDao widgetDao;

    @Override
    public Widget findById(Long id) {
        checkNotNull(id);
        return widgetDao.findById(id);
    }

    @Override
    public List<Widget> pageLevelWidget(Long pageId) {
        return widgetDao.pageWidgets(pageId);
    }

    @Override
    public List<Widget> siteLevelWidgets(Widget.BelongType belongType, Long siteInstanceId) {
        return widgetDao.siteLevelWidgets(belongType, siteInstanceId);
    }

    @Override
    public List<Widget> itemDescWidgets(Long itemId) {
        return widgetDao.itemDescWidgets(itemId);
    }

    @Override
    public Widget createRootWidget(Long belongId, Widget.BelongType belongType) {
        String componentPath = null;
        switch (belongType) {
            case FOOTER:
                componentPath = "common/page/footer";
                break;
            case HEADER:
                componentPath = "common/page/header";
                break;
            case PAGE:
                componentPath = "common/page/body";
        }
        checkNotNull(componentPath, "no root component defined for : " + belongType.toString());
        Component component = componentService.findByPath(componentPath);
        checkNotNull(component, "component not found for:" + componentPath);
        return _addNewWidget(belongId, belongType.toNumber(), null, component.getId());//root widget without parent

    }

    @Override
    public Widget addNewWidget(Long pageWidgetId, Long compId) {
        checkNotNull(pageWidgetId, "pageWidgetId can not be null!");
        Widget pageWidget = widgetDao.findById(pageWidgetId);
        checkNotNull(pageWidget, "page not found for Id: " + pageWidget);
        return _addNewWidget(pageWidget.getBelongId(), pageWidget.getBelongType(), pageWidget.getId(), compId);
    }

    @Override
    public Widget addItemWidget(Long itemId, Long compId) {
        return _addNewWidget(itemId, Widget.BelongType.ITEM.toNumber(), null, compId);
    }

    private Widget _addNewWidget(Long belongId, Integer belongType, Long parentId, Long compId) {
        checkNotNull(belongId, "belongId can not be null!");
        checkNotNull(belongType, "belongType can not be null!");
        checkNotNull(compId, "componentId can not be null!");

        Widget widget = new Widget();
        widget.setBelongId(belongId);
        widget.setBelongType(belongType);
        widget.setParentId(parentId);
        widget.setCompId(compId);

        widgetDao.create(widget);
        logger.debug("widget created - [id:{},belongId:{},belongType:{},parentId:{},componentId:{}]",
                widget.getId(), widget.getBelongId(), widget.getBelongType(), widget.getParentId(), widget.getCompId());
        return widget;
    }

    @Override
    public void updateParams(Long widgetId, String jsonData) {
        checkNotNull(widgetId);
        checkNotNull(jsonData);
        widgetDao.updateJsonData(widgetId, CharMatcher.is('\n').removeFrom(jsonData));
    }

    @Override
    public void updateBehavior(Long widgetId, String behavior) {
        checkNotNull(widgetId);
        checkNotNull(behavior);
        widgetDao.updateBehavior(widgetId, behavior);
    }

    @Override
    public void saveItemDescStructure(Long itemId, String structureJson) {
        checkNotNull(itemId);

        List<Widget> widgets = recreateWidgetFromJson(structureJson);
        if (widgets == null) {
            return;
        }
        widgetDao.batchUpdateDetailWidgets(itemId, widgets);
    }

    @Override
    public void savePageStructure(Long siteInstanceId, Long pageId, String structureJson) {
        checkNotNull(siteInstanceId);
        checkNotNull(pageId);

        List<Widget> widgets = recreateWidgetFromJson(structureJson);
        if (widgets == null) {
            return;
        }

        widgetDao.batchUpdate(siteInstanceId, pageId, widgets);

    }

    private List<Widget> recreateWidgetFromJson(String structureJson) {
        if (Strings.isNullOrEmpty(structureJson)) {
            return null;
        }
        Map<Long, Map<String, Object>> pageData = JsonMapper.nonDefaultMapper().fromJson(structureJson,
                JsonMapper.nonDefaultMapper().createCollectionType(Map.class, Long.class, Map.class));

        if (pageData != null) {
            List<Widget> widgets = Lists.newArrayList();
            for (Long id : pageData.keySet()) {
                Widget widget = new Widget();
                widget.setId(id);
                Map<String, Object> style = pageData.get(id);
                widget.setMode((String) style.remove("mode"));
                widget.setBehavior((String) style.remove("behavior"));

                String parentId = (String) style.remove("parentId");
                //update attributes : parentId,belongId,belongType,style
                //clone belongId and belongType form parent widget.
                if (Strings.isNullOrEmpty(parentId)) {
                    Widget inStorage = widgetDao.findById(widget.getId());
                    widget.setBelongId(inStorage.getBelongId());
                    widget.setBelongType(inStorage.getBelongType());
                } else {
                    widget.setParentId(Long.parseLong(parentId));
                    Widget parent = widgetDao.findById(widget.getParentId());
                    if (parent != null) {
                        widget.setBelongId(parent.getBelongId());
                        widget.setBelongType(parent.getBelongType());
                    }
                }
                widget.setStyle(CssProcessor.process(style));
                if(widget.getBelongId() !=null&&widget.getBelongType() != null){
                    widgets.add(widget);
                } else{
                    logger.error("found orphan {},its belong type or belong id is null",widget);
                }
            }
            return widgets;
        }
        return null;
    }

    @Override
    public void delete(Long widgetId, List<Long> children) {

        TreeNode root = checkRelation(widgetId, children);
        final List<Long> ids = Lists.newArrayList();
        TreeBuilder.travel(root, new NodeVisitor() {
            @Override
            public String visit(Node node) {
                ids.add(node.getId());
                return null;
            }
        });
        widgetDao.batchDelete(ids);
    }

    @Override
    public Widget clone(Long widgetId, List<Long> children) {
        TreeNode root = checkRelation(widgetId, children);
        //for return
        final StringBuilder id_nid_comps = new StringBuilder();
        //for change parentId from clone source' id to newId
        final Map<Long, Long> idMapping = Maps.newHashMap();

        //gen id and id-new-id mapping
        TreeBuilder.travel(root, new NodeVisitor() {
            @Override
            public String visit(Node node) {
                Widget widget = (Widget) node;
                idMapping.put(widget.getId(), widgetDao.newId());
                return null;
            }
        });

        //change to new-id ,and save and gen string for return
        TreeBuilder.travel(root, new NodeVisitor() {
            @Override
            public String visit(Node node) {
                Widget widget = (Widget) node;
                //gen return string(0)
                id_nid_comps.append(";").append(widget.getId()).append(",");

                //change to new-id
                widget.setId(idMapping.get(widget.getId()));

                //root's id not in idMapping and should not be changed.
                if (idMapping.containsKey(widget.getParentId())) {
                    widget.setParentId(idMapping.get(widget.getParentId()));
                }
                //save
                widgetDao.create(widget.getId(), widget);

                //gen return string(1,2,3)
                Component component = componentService.findById(widget.getCompId());
                id_nid_comps.append(widget.getId()).append(",").
                        append(widget.getParentId()).append(",").
                        append(component.getPath().replace("/", "_"));

                return null;
            }
        });
        Widget ret = new Widget();
        ret.setId(root.current.getId());
        ret.setMode(id_nid_comps.toString().replaceFirst(";", ""));
        return ret;
    }

    /**
     * parent关系校验,只有检查通过的才在操作列表中,防止恶意删除
     *
     * @param widgetId 删除的最顶层widget的id
     * @param children 其下的ids
     * @return 以最顶层widget为根的树形结构, 非合法节点会被抛弃
     */
    private TreeNode checkRelation(Long widgetId, List<Long> children) {

        List<Widget> widgets = Lists.newArrayList();
        Widget rootWidget = widgetDao.findById(widgetId);
        Long parentId = rootWidget.getParentId();
        rootWidget.setParentId(null);
        widgets.add(rootWidget);
        if (children != null) {
            for (Long child : children) {
                widgets.add(widgetDao.findById(child));
            }
        }
        TreeNode root = TreeBuilder.buildTree(widgets);

        checkState(widgetId.equals(root.current.getId()), "root not equals input widgetId");
        rootWidget.setParentId(parentId);
        return root;
    }
}