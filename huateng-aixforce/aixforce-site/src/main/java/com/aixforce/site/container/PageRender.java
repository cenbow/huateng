/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.container;

import com.aixforce.common.utils.JsonMapper;
import com.aixforce.site.container.exception.NotFound404Exception;
import com.aixforce.site.container.exception.Server500Exception;
import com.aixforce.site.handlebars.HandlebarEngine;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.site.model.redis.Widget;
import com.aixforce.site.service.PageService;
import com.aixforce.site.service.SiteInstanceService;
import com.aixforce.site.service.SiteService;
import com.aixforce.site.service.WidgetService;
import com.aixforce.user.base.BaseUser;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Desc:处理页面浏览
 * Author: dimzfw@gmail.com
 * Date: 8/17/12 3:30 PM
 */
@Service
public class PageRender {
    private static final Logger log = LoggerFactory.getLogger(PageRender.class);

    @Autowired
    private WidgetService widgetService;
    @Autowired
    private SiteInstanceService siteInstanceService;
    @Autowired
    private SiteService siteService;
    @Autowired
    private PageService pageService;
    @Autowired
    private BlockBuilder blockBuilder;
    @Autowired
    private HandlebarEngine handlebarEngine;

    /**
     * 通过domain和path来渲染页面
     *
     * @param domain       request domain
     * @param path         request path
     * @param context      上下文，包括页面请求参数
     * @param view         true is view and false is design
     * @param rendHeadFoot 是否渲染头尾部分，渲染商品详情页面时，不要渲染头尾
     * @throws NotFound404Exception 未找到对应的页面
     */
    public boolean render(String domain, String path, Map<String, Object> context, boolean view, boolean rendHeadFoot) {
        checkArgument(!Strings.isNullOrEmpty(domain), "domain can not be empty!");
        checkArgument(!Strings.isNullOrEmpty(path), "path can not be empty!");
        //拿站点
        Site site = siteService.findByDomain(domain);

        if (site == null) {
            log.error("no site found for domain ({}) ", domain);
            throw new NotFound404Exception("site not found");
        }
        return render(site, path, context, view, rendHeadFoot);
    }

    /**
     * 通过domain和path来渲染页面
     *
     * @param site         request site
     * @param path         request path
     * @param context      上下文，包括页面请求参数
     * @param view         true is view and false is design
     * @param rendHeadFoot 是否渲染头尾部分
     * @throws NotFound404Exception 未找到对应的页面
     */
    public boolean render(Site site, String path, Map<String, Object> context, boolean view, boolean rendHeadFoot)
            throws NotFound404Exception {
        //站点累计pv和今日pv 均要+1
        //siteService.incrementPv(site.getId());

        SiteInstance siteInstance;
        if (view) {//拿发布后实例中的页头、页尾
            if (site.getReleaseSiteInstanceId() == null) {
                log.warn("site(id={}) not released yet, try to render layout", site.getId());
                _naiveRender(site, path, context);
                return false;
            }
            siteInstance = siteInstanceService.findById(site.getReleaseSiteInstanceId());
        } else { //拿装修中实例中的页头、页尾
            if (site.getDefaultSiteInstanceId() == null) {
                log.error("site(id={}) has no defaultSiteInstance.", site.getId());
                throw new NotFound404Exception("site has no defaultSiteInstance");
            }
            siteInstance = siteInstanceService.findById(site.getDefaultSiteInstanceId());
        }

        if (siteInstance == null) {
            if (view) {
                log.warn("no release siteInstance found for site(id={}), try to render layout", site.getId());
                _naiveRender(site, path, context);
                return false;
            } else {
                log.error("no design siteInstance found for site(id={})", site.getId());
                throw new NotFound404Exception("design siteInstance not found");
            }
        }

        //拿页面
        Page page;
        //增加角色判断,显示不同页面
        if (TempRoleSupportPage.isRoleSupport(path) && UserUtil.getCurrentUser() != null) {
            BaseUser baseUser = UserUtil.getCurrentUser();
            if (baseUser.getTypeEnum() == BaseUser.TYPE.ADMIN || baseUser.getTypeEnum() == BaseUser.TYPE.DESIGNER) {
                page = pageService.findBySiteInstanceAndPath(siteInstance.getId(), path + "-designer");
                //改回之前的path,用于渲染
                page.setPath(path);
            }else{
                page = pageService.findBySiteInstanceAndPath(siteInstance.getId(), path);
            }
        } else {
            page = pageService.findBySiteInstanceAndPath(siteInstance.getId(), path);
        }

        if (page == null) {
            log.warn("page not found for siteInstance(id={})'s path({}), try to render layout", siteInstance.getId(), path);
            _naiveRender(site, path, context);
            return false;
        }

        _render(site, siteInstance, page, context, view, rendHeadFoot);
        return true;
    }

    /**
     * 通过PageId渲染
     */
    public Long render(Long pageId, Map<String, Object> context) {
        Page page = pageService.findById(pageId);
        if (page == null) {
            log.error("page not found for (id={})", pageId);
            throw new NotFound404Exception("page not found");
        }

        SiteInstance siteInstance = siteInstanceService.findById(page.getSiteInstanceId());

        if (siteInstance == null) {
            log.error("no design siteInstance found for page(id={})", page);
            throw new NotFound404Exception("design siteInstance not found");
        }

        _render(null, siteInstance, page, context, false, true);

        return page.getSiteInstanceId();
    }

    public String renderPageBody(Long pageId, Map<String, Object> context) {
        Map<String, String> uniqueComponents = Maps.newHashMap();

        return renderPageBody(pageId, context, uniqueComponents, false);
    }

    private String renderPageBody(Long pageId, Map<String, Object> context, Map<String, String> uniqueComponents, boolean view) {
        List<Widget> pageWidgets = widgetService.pageLevelWidget(pageId);
        checkArgument(pageWidgets != null && pageWidgets.size() > 0, "page's widgets is empty! pageId - [%s].", pageId);

        String ret = blockBuilder.compose(context, pageWidgets, uniqueComponents);

        ret += "<input id=\"pageId\" type=\"hidden\" value=\"" + pageId + "\">";

        //页面组件名称统计，用于前端做组件初始化
        ret += "<input id=\"uniqueComponents\" type=\"hidden\" value=\"" + Joiner.on(",").join(uniqueComponents.keySet()).replace("/", "_") + "\">";

        //组件样式配置信息
        if (!view) {
            ret += "<input id=\"pageData\" type=\"hidden\" value='" + genPageDataJson(pageWidgets) + "'>";
        }
        return ret;

    }

    /**
     * 直接按照path找到layout来渲染
     *
     * @param path    路径
     * @param context 上下文
     */
    private void _naiveRender(Site site, String path, Map<String, Object> context) throws NotFound404Exception {
        String realPath;
        if (StringUtils.isEmpty(site.getRootPath())) {
            realPath = "/layout/" + path;
        } else {
            realPath = "/layout/" + site.getRootPath() + "/" + path;
        }
        try {
            context.put(RenderConstants.HTML, handlebarEngine.naiveExec(null, realPath.replace("//","/"), context, false));
        } catch (FileNotFoundException e) {
            log.error("failed to find page {},cause:{}", realPath, e.getMessage());
            throw new NotFound404Exception("page not found");
        }
    }

    private void _render(Site site, SiteInstance siteInstance, Page page, Map<String, Object> context, boolean view, boolean rendHeadFoot) {

        Map<String, String> uniqueComponents = Maps.newHashMap();

        context.put(RenderConstants.SITE, site);
        context.put(RenderConstants.SITE_INSTANCE, siteInstance);
        context.put(RenderConstants.PAGE, page);

        //是否需要渲染头尾
        if (rendHeadFoot) {
            List<Widget> headerWidgets = widgetService.siteLevelWidgets(Widget.BelongType.HEADER, page.getSiteInstanceId());

            List<Widget> footerWidgets = widgetService.siteLevelWidgets(Widget.BelongType.FOOTER, page.getSiteInstanceId());

            if (headerWidgets.isEmpty()) {
                log.error("header's widgets is empty for siteInstance(id={}) ", siteInstance.getId());
                throw new Server500Exception("header's widgets is empty");
            }

            if (footerWidgets.isEmpty()) {
                log.error("footer's widgets is empty for siteInstance(id={}) ", siteInstance.getId());
                throw new Server500Exception("footer's widgets is empty");
            }
            if (context.get("_preview") != null && site.getForkable() == 1) {   //TODO 只有模板的网站可以这样
                context.put("preview_bar", blockBuilder.composeOfficialPreviewBar(context, uniqueComponents));
            }

            context.put("header", blockBuilder.compose(context, headerWidgets, uniqueComponents));
            context.put("footer", blockBuilder.compose(context, footerWidgets, uniqueComponents));
            if (!view) {
                List<Widget> all = Lists.newArrayList(headerWidgets);
                all.addAll(footerWidgets);
                context.put("_HEAD_FOOT_DATA_", genPageDataJson(all));
            }
        }

        context.put("body", renderPageBody(page.getId(), context, uniqueComponents, view));
        context.put("siteInstanceId", siteInstance.getId());
        context.put("siteId", siteInstance.getSiteId());
        context.put("title", page.getTitle());
        context.put("style", siteInstance.getStyle());

    }

    protected static String genPageDataJson(List<Widget> widgets) {
        //生成design需要的PageData {id:{k:v},id{k:v}}
        Map<Long, Map<String, String>> pageData = Maps.newHashMap();
        for (Widget widget : widgets) {
            Map<String, String> style = Maps.newHashMap();
            if (!Strings.isNullOrEmpty(widget.getStyle())) {
                String[] kvs = widget.getStyle().split(";");   //background-color:rgb();background-color:rgba(),后者正好覆盖前者
                if (kvs != null) {
                    for (String kv : kvs) {
                        String[] _kv = kv.split(":", 2);
                        if (_kv != null && _kv.length == 2 && !Strings.isNullOrEmpty(_kv[0])) {
                            style.put(_kv[0], StringUtils.trimLeadingCharacter(_kv[1], ' '));
                        }
                    }
                }
            }
            if (widget.getParentId() != null) {
                style.put("parentId", widget.getParentId().toString());
            }
            if (!Strings.isNullOrEmpty(widget.getMode())) {
                style.put("mode", widget.getMode());
            }
            pageData.put(widget.getId(), style);

        }
        return JsonMapper.nonDefaultMapper().toJson(pageData);
    }
}