/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.site.dao.redis.RedisPageDao;
import com.aixforce.site.dao.redis.RedisWidgetDao;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.site.model.redis.Widget;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/20/12 11:46 AM
 */
@Service("pageService")
public class PageServiceImpl implements PageService {
    private static Logger logger = LoggerFactory.getLogger(PageServiceImpl.class);

    @Autowired
    private RedisPageDao pageDao;
    @Autowired
    private RedisWidgetDao widgetDao;
    @Autowired
    private WidgetService widgetService;


    @Override
    public void createOrUpdate(Page page) {
        checkArgument(page.getSiteInstanceId() != null);
        if (page.getId() == null) {
            if (Strings.isNullOrEmpty(page.getName())) {
                page.setName("新页面");
            }
            pageDao.create(page);
            logger.debug("page created - [id:{},name:{},path:{}]", page.getId(), page.getName(), page.getPath());

            if (Strings.isNullOrEmpty(page.getPath())) {
                page.setPath("" + page.getId());
                pageDao.update(page);
                logger.debug("path is null,update to pageId - [{}]", page.getId());
            }

            //create widget for page
            Widget widget = widgetService.createRootWidget(page.getId(), Widget.BelongType.PAGE);
            logger.debug("page's widget created - [widgetId:{}]", widget.getId());
        } else {
            if(page.getOnNav()==null){
                page.setOnNav("");
            }
            pageDao.update(page);
        }
    }

    @Override
    public Page findById(Long id) {
        return pageDao.findById(id);
    }

    @Override
    public void delete(Long id) {
        //delete all widgets
        List<Widget> allWidgets = widgetDao.pageWidgets(id);

        List<Long> ids = Lists.newArrayList();
        for (Widget widget : allWidgets) {
            ids.add(widget.getId());
        }

        widgetDao.batchDelete(ids);
        //delete page
        pageDao.delete(id);
    }

    @Override
    public Page findBySiteInstanceAndPath(Long siteInstanceId, String path) {
        checkNotNull(siteInstanceId, "siteInstance's id can not be null！");
        checkArgument(!Strings.isNullOrEmpty(path), "path can not be null or empty!");
        return pageDao.findByInstanceIdAndPath(siteInstanceId, path);
    }

    @Override
    public List<Page> findBySiteInstanceId(Long siteInstanceId) {
        checkNotNull(siteInstanceId, "siteInstance's id can not be null！");
        return pageDao.findBySiteInstanceId(siteInstanceId);
    }

    @Override
    public List<Page> findBySiteInstance(SiteInstance siteInstance) {
        List<Page> pageList = findBySiteInstanceId(siteInstance.getId());

        Iterables.removeIf(pageList, new Predicate<Page>() { //remove selected category
            @Override
            public boolean apply(Page page) {
                return Strings.isNullOrEmpty(page.getOnNav());
            }
        });
        return pageList;
    }
}
