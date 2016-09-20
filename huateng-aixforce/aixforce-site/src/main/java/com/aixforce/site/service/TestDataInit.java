/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.model.redis.Component;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.user.base.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Desc:only for test
 * Author: dimzfw@gmail.com
 * Date: 8/22/12 4:46 PM
 */
@Service
public class TestDataInit {
    @Autowired
    private SiteService siteService;
    @Autowired
    private SiteInstanceService siteInstanceService;
    @Autowired
    private ComponentService componentService;
    @Autowired
    private PageService pageService;
    @Autowired
    private WidgetService widgetService;

    public Site site;
    public Page page;
    public Long containerComponentId;

    public void initComponents() {
        createComponent("common/page/header");
        createComponent("common/page/body");
        createComponent("common/page/footer");
        containerComponentId = createComponent("box");
    }

    public void init() {
        //init components : header footer and page
        initComponents();
        //init site
        site = new Site();
        site.setDomain("www.aixforce.com");
        site.setType(Site.Status.NORMAL.toNumber());
        site.setName("aixforce");
        site.setSubDomain("x");
        BaseUser user = new BaseUser(-111L, "test@aixforce.com", BaseUser.TYPE.ADMIN);
        siteService.create(user.getId(), site);

        //init siteInstance
        SiteInstance siteInstance = new SiteInstance();
        siteInstance.setName("DefaultSite");
        siteInstance.setSiteId(site.getId());
        siteInstance.setStatus(SiteInstance.Status.NORMAL.toNumber());
        siteInstanceService.create(siteInstance, true);

        //update default siteInstanceId
        siteService.setDefault(site.getId(), siteInstance.getId());

        //new page
        page = new Page();
        page.setTitle("aixforce");
        page.setSiteInstanceId(siteInstance.getId());
        page.setPath("/index");
        page.setRank(1);
        pageService.createOrUpdate(page);
    }

    public Long createComponent(String name) {
        try {
            Component component = new Component();
            component.setName(name);
            component.setPath(name);
            component.setCompCategoryId(2L);
            componentService.create(component);
            return component.getId();
        } catch (Exception e) {
            e.printStackTrace();

            return -1L;
        }
    }

    public Long container1;
    Long container2;
    Long container3;

    public void addSomeWidgets() {
        //add 2 container component
        container1 = widgetService.addNewWidget(page.getId(), containerComponentId).getId();

        container2 = widgetService.addNewWidget(page.getId(), containerComponentId).getId();
        //add a container on container
        container3 = widgetService.addNewWidget(page.getId(), containerComponentId).getId();
        widgetService.addNewWidget(page.getId(), containerComponentId);
        widgetService.addNewWidget(page.getId(), containerComponentId);


    }

    public void deleteWidget() {
        widgetService.delete(container3,null);
    }

}
