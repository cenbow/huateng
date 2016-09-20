/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.controller.design;

import com.aixforce.site.container.PageRender;
import com.aixforce.site.container.SecurityHelper;
import com.aixforce.site.container.exception.NotFound404Exception;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.service.PageService;
import com.aixforce.site.service.SiteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/27/12 10:11 AM
 */
@Controller
@RequestMapping("/design")
public class DesignView {
    private static final Logger log = LoggerFactory.getLogger(DesignView.class);
    @Autowired
    private PageRender pageRender;

    @Autowired
    private PageService pageService;

    @Autowired
    private SiteService siteService;

    @Autowired
    @Qualifier("defaultSecurityHelper")
    private SecurityHelper securityHelper;

    /**
     * view default page
     *
     * @param siteId site instance's id
     * @return page's body in edit state
     */
    @RequestMapping(value = "/{siteId}/index", method = RequestMethod.GET)
    public String viewDefaultPage(@PathVariable("siteId") long siteId,
                                  WebRequest request, Map<String, Object> context) {

        securityHelper.checkPermission(siteId, SecurityHelper.ResourceType.Site);
        Site site = siteService.findById(siteId);
        if (site == null) {
            log.error("site(id={}) not found", siteId);
            throw new NotFound404Exception("site not find");
        }
        Long siteInstanceId = site.getDefaultSiteInstanceId();
        Page page = this.pageService.findBySiteInstanceAndPath(siteInstanceId, "index");
        if (page == null) {
            log.error("default page (path='index') not found for site(id={}) and siteInstance(id={})", siteId, siteInstanceId);
            throw new NotFound404Exception("default page (path='index') not found");
        }
        return _viewPage(page.getId(), request, context);
    }

    /**
     * view page by id , include header and footer
     *
     * @param pageId page id
     * @return page's body in edit state
     */
    @RequestMapping(value = "/{pageId}", method = RequestMethod.GET)
    public String viewPage(@PathVariable("pageId") long pageId, WebRequest request, Map<String, Object> context) {
        securityHelper.checkPermission(pageId, SecurityHelper.ResourceType.Page);
        return _viewPage(pageId, request, context);
    }

    /**
     * view page body,without header and footer
     *
     * @param pageId page id
     * @return page body exclude header and footer
     */
    @RequestMapping(value = "/body/{pageId}", method = RequestMethod.GET)
    @ResponseBody
    public String viewPageBody(@PathVariable("pageId") long pageId, WebRequest request, Map<String, Object> context) {
        securityHelper.checkPermission(pageId, SecurityHelper.ResourceType.Page);
        for (String name : request.getParameterMap().keySet()) {
            context.put(name, request.getParameter(name));
        }
        return pageRender.renderPageBody(pageId, context);
    }

    private String _viewPage(Long pageId, WebRequest request, Map<String, Object> context) {
        for (String name : request.getParameterMap().keySet()) {
            context.put(name, request.getParameter(name));
        }
        pageRender.render(pageId, context);
        return "resource:design";
    }
}