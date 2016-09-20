/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.dao.redis.RedisSiteInstanceDao;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.site.model.redis.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/20/12 11:45 AM
 */
@Service("siteInstanceService")
public class SiteInstanceServiceImpl implements SiteInstanceService {
    private static Logger logger = LoggerFactory.getLogger(SiteInstanceServiceImpl.class);

    @Autowired
    private RedisSiteInstanceDao siteInstanceDao;

    @Autowired
    private WidgetService widgetService;

    @Autowired
    private PageService pageService;

    @Override
    public Long create(SiteInstance siteInstance, boolean withPagePart) {
        checkNotNull(siteInstance.getSiteId());
        //1.create site-instance's new id
        siteInstance.setId(siteInstanceDao.newId());

        logger.debug("siteInstance created - [id:{},name:{},siteId:{}]",
                siteInstance.getId(), siteInstance.getName(), siteInstance.getSiteId());

        if (withPagePart) {
            //2.create header and footer for this site-instance
            Widget header = widgetService.createRootWidget(siteInstance.getId(), Widget.BelongType.HEADER);
            logger.debug("siteInstance's header widget created - [header widgetId:{}]", header.getId());

            Widget footer = widgetService.createRootWidget(siteInstance.getId(), Widget.BelongType.FOOTER);
            logger.debug("siteInstance's footer widget created - [footer widgetId:{}]", footer.getId());

            //3.create index page
            Page page = new Page();
            page.setSiteInstanceId(siteInstance.getId());
            page.setPath("index");
            page.setRank(1);
            pageService.createOrUpdate(page);  // auto create page's widget

            //4.set default page for siteInstance
            siteInstance.setDefaultPageId(page.getId());
            siteInstanceDao.create(siteInstance.getId(), siteInstance);
        }
        return siteInstance.getId();
    }

    @Override
    public SiteInstance findById(Long id) {
        checkNotNull(id, "site id can not be null!");
        return siteInstanceDao.findById(id);
    }

    @Override
    public void update(SiteInstance siteInstance) {
        siteInstanceDao.update(siteInstance);
    }
}