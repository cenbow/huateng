/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller.design;


import com.aixforce.site.container.RenderConstants;
import com.aixforce.site.container.SecurityHelper;
import com.aixforce.site.handlebars.HandlebarEngine;
import com.aixforce.site.model.redis.Component;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.site.model.redis.Widget;
import com.aixforce.site.service.ComponentService;
import com.aixforce.site.service.PageService;
import com.aixforce.site.service.WidgetService;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 对widget的各种操作
 * Author: dimzfw@gmail.com
 * Date: 8/27/12 10:11 AM
 */
@Controller
@RequestMapping("/design/widget")
public class DesignWidget {
    private static Logger logger = LoggerFactory.getLogger(DesignWidget.class);

    @Autowired
    private ComponentService componentService;

    @Autowired
    private WidgetService widgetService;

    @Autowired
    private PageService pageService;

    @Autowired
    private HandlebarEngine handlebarEngine;

    @Autowired
    @Qualifier("defaultSecurityHelper")
    private SecurityHelper securityHelper;


    /**
     * in default, new component always added into Page
     *
     * @param pageRootWidgetId page's root widget's id
     * @param compId           added component's id
     * @return new widget's id
     */
    @RequestMapping(value = "/{pageRootWidgetId}/{compId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addyComponentToPage(@PathVariable("pageRootWidgetId") long pageRootWidgetId, @PathVariable("compId") long compId) {
        securityHelper.checkPermission(pageRootWidgetId, SecurityHelper.ResourceType.Widget);

        Widget widget = widgetService.addNewWidget(pageRootWidgetId, compId);
        return addComponent(widget, compId);
    }


    /**
     * add component to item's description
     *
     * @param itemId item's id
     * @param compId added component's id
     * @return new widget's id
     */
    @RequestMapping(value = "item/{itemId}/{compId}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addComponentToItem(@PathVariable("itemId") long itemId, @PathVariable("compId") long compId) {
        //TODO check permission

        //check is item's description root widget exist?
        List<Widget> itemWidgets = widgetService.itemDescWidgets(itemId);
        Widget itemRoot = null;
        if (itemWidgets == null || itemWidgets.size() == 0) {
            //create root
            itemRoot = widgetService.createRootWidget(itemId, Widget.BelongType.ITEM);
        } else {
            for (Widget widget : itemWidgets) {
                if (widget.getParentId() == null) {
                    itemRoot = widget;
                    break;
                }
            }
        }
        if (itemRoot == null) {
            return null;
        }
        Widget widget = widgetService.addNewWidget(itemRoot.getId(), compId);
        return addComponent(widget, compId);
    }

    private Map<String, Object> addComponent(Widget widget, Long compId) {

        Component component = componentService.findById(compId);

        Map<String, Object> ret = Maps.newHashMap();
        ret.put("id", widget.getId());
        ret.put("template_name", component.getName());
        ret.put("template_path", component.getPath());


        Map<String, Object> params = Maps.newHashMap();

        //execute service
        if (component.getApis() != null) {

            Long pageId = widget.getBelongId();
            Page belongPage = pageService.findById(pageId);
            SiteInstance belongSiteInstance = new SiteInstance();
            belongSiteInstance.setId(belongPage.getSiteInstanceId());

            params.put(RenderConstants.SITE_INSTANCE, belongSiteInstance);
            params.put(RenderConstants.PAGE, belongPage);
        }
        //模块执行后再返回
        logger.debug("Call handbarEngine for component : {}",component.getPath());
        ret.put("html", handlebarEngine.execComponent(widget, component, params));
        return ret;
    }

    /**
     * get widget param
     *
     * @param widgetId widget's id
     */
    @RequestMapping(value = "param/{widgetId}", method = RequestMethod.GET)
    public ResponseEntity<String> getParam(@PathVariable("widgetId") Long widgetId) {
        securityHelper.checkPermission(widgetId, SecurityHelper.ResourceType.Widget);
        Widget widget = widgetService.findById(widgetId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));

        String ret = widget.getJsonData() == null ? "" : widget.getJsonData();
        headers.setContentLength(ret.getBytes(Charsets.UTF_8).length);

        return new ResponseEntity<String>(ret, headers, HttpStatus.OK);
    }

    /**
     * update widget's params
     *
     * @param widgetId widget's id
     * @param params   widget's params, json string
     */
    @RequestMapping(value = "param/{widgetId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void updateParam(@PathVariable("widgetId") Long widgetId, @RequestParam("data") String params) {
        securityHelper.checkPermission(widgetId, SecurityHelper.ResourceType.Widget);
        widgetService.updateParams(widgetId, params);
    }

    /**
     * update widget's behavior
     *
     * @param widgetId widget's id
     * @param behavior widget's behavior, json string
     */
    @RequestMapping(value = "behavior/{widgetId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void updateStyle(@PathVariable("widgetId") long widgetId, @RequestParam("data") String behavior) {
        securityHelper.checkPermission(widgetId, SecurityHelper.ResourceType.Widget);
        widgetService.updateBehavior(widgetId, behavior);
    }

    /**
     * delete widget by id
     *
     * @param widgetId widget's id
     */
    @RequestMapping(value = "delete/{widgetId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void delete(@PathVariable("widgetId") Long widgetId, @RequestParam String children) {
        securityHelper.checkPermission(widgetId, SecurityHelper.ResourceType.Widget);
        widgetService.delete(widgetId, toArray(children));
    }

    /**
     * clone widget by id
     *
     * @param widgetId widget's id
     */
    @RequestMapping(value = "clone/{widgetId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Widget clone(@PathVariable("widgetId") Long widgetId, @RequestParam String children) {
        securityHelper.checkPermission(widgetId, SecurityHelper.ResourceType.Widget);
        return widgetService.clone(widgetId, toArray(children));
    }

    private List<Long> toArray(String children) {

        if (!Strings.isNullOrEmpty(children)) {
            List<Long> ids = Lists.newArrayList();
            for (String id : children.split(",")) {
                ids.add(Long.parseLong(id));
            }
            return ids;
        }
        return null;
    }
}
