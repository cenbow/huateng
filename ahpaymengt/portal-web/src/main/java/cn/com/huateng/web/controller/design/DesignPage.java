/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller.design;

import com.aixforce.common.utils.NameValidator;
import com.aixforce.site.container.SecurityHelper;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.site.service.PageService;
import com.aixforce.site.service.SiteInstanceService;
import com.aixforce.site.service.WidgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/27/12 10:11 AM
 */
@Controller
@RequestMapping("/design/page")
public class DesignPage {
    @Autowired
    private SiteInstanceService siteInstanceService;

    @Autowired
    private PageService pageService;

    @Autowired
    private WidgetService widgetService;

    @Autowired
    @Qualifier("defaultSecurityHelper")
    private SecurityHelper securityHelper;

    /**
     * list pages
     *
     * @return new page data in json
     */
    @RequestMapping(value = "pages/{siteInstanceId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Page> list(@PathVariable("siteInstanceId") long siteInstanceId) {
        securityHelper.checkPermission(siteInstanceId, SecurityHelper.ResourceType.SiteInstance);
        return pageService.findBySiteInstanceId(siteInstanceId);
    }

    /**
     * add page or update page's info
     *
     * @return new page data in json
     */
    @RequestMapping(value = "pages/{siteInstanceId}", method = RequestMethod.POST)
    @ResponseBody
    public Page add(@PathVariable("siteInstanceId") long siteInstanceId, Page page) {
        securityHelper.checkPermission(siteInstanceId, SecurityHelper.ResourceType.SiteInstance);
        page.setSiteInstanceId(siteInstanceId);
        if(!NameValidator.isAllowedPath(page.getPath())){
            throw new IllegalArgumentException("路径不合法");
        }
        pageService.createOrUpdate(page);
        return page;
    }

    /**
     * delete page by id
     *
     * @param pageId page's id.
     */
    @RequestMapping(value = "/{pageId}", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(@PathVariable("pageId") long pageId) {
        securityHelper.checkPermission(pageId, SecurityHelper.ResourceType.Page);
        pageService.delete(pageId);
        return null;
    }

    /**
     * get page info.
     *
     * @param pageId page's id.
     * @return page object to json
     */
    @RequestMapping(value = "edit/{pageId}", method = RequestMethod.GET)
    @ResponseBody
    public Page getEditData(@PathVariable("pageId") long pageId) {
        securityHelper.checkPermission(pageId, SecurityHelper.ResourceType.Page);
        return pageService.findById(pageId);
    }

    /**
     * save page's structure
     *
     * @param structureJson page's widgets' structure data ,include position and size in style field, and self's id and parent widget's id
     */
    @RequestMapping(value = "structure/{siteInstanceId}/{pageId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void savePageStructure(@PathVariable("siteInstanceId") Long siteInstanceId, @PathVariable("pageId") Long pageId, String structureJson) {
        securityHelper.checkPermission(siteInstanceId, SecurityHelper.ResourceType.SiteInstance);
        widgetService.savePageStructure(siteInstanceId, pageId, structureJson);
    }

    @RequestMapping(value = "global/{siteInstanceId}", method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveGlobalStyle(@PathVariable("siteInstanceId") Long siteInstanceId, String style) {
        securityHelper.checkPermission(siteInstanceId, SecurityHelper.ResourceType.SiteInstance);
        SiteInstance siteInstance = new SiteInstance();
        siteInstance.setId(siteInstanceId);
        siteInstance.setStyle(style);
        siteInstanceService.update(siteInstance);
    }
}
