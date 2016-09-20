/*
 * Copyright (c) 2013 杭州端点网络科技有限公司
 */

package com.aixforce.site.dao.redis;

import com.aixforce.common.utils.CommonConstants;
import com.aixforce.redis.utils.JedisTemplate;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.site.model.redis.Widget;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.aixforce.redis.utils.KeyUtils.*;


/*
 * Author: jlchen
 * Date: 2013-01-15
 */
@Repository
public class SiteManager {

    @Autowired
    private CommonConstants commonConstants;

    @Autowired
    private JedisTemplate template;

    @Autowired
    private RedisSiteDao siteDao;

    @Autowired
    private RedisSiteInstanceDao siteInstanceDao;

    @Autowired
    private RedisPageDao pageDao;

    @Autowired
    private RedisWidgetDao widgetDao;


    private Set<String> pageIdsOf(final Long siteInstanceId) {
        return template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(pages(siteInstanceId));
            }
        });
    }

    private Set<String> headerWidgetIdsOf(final Long siteInstanceId) {
        return template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(headerWidgets(siteInstanceId));
            }
        });
    }

    private Set<String> footerWidgetIdsOf(final Long siteInstanceId) {
        return template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(footerWidgets(siteInstanceId));
            }
        });
    }

    private Set<String> widgetIdsOf(final Long pageId) {
        return template.execute(new JedisTemplate.JedisAction<Set<String>>() {
            @Override
            public Set<String> action(Jedis jedis) {
                return jedis.smembers(widgets(pageId));
            }
        });
    }


    private void deleteWidgets(Transaction t, Iterable<String> ids) {
        if (ids == null || Iterables.isEmpty(ids)) {
            return;
        }
        t.del(Iterables.toArray(Iterables.transform(ids, new Function<String, String>() {
            @Override
            public String apply(String input) {
                return entityId(Widget.class, input);
            }
        }), String.class));
    }


    private void saveWidget(Transaction t, Widget widget) {
        t.hmset(entityId(Widget.class, widget.getId()), widgetDao.stringHashMapper.toHash(widget));
    }

    private void savePage(Transaction t, Page page) {
        t.hmset(entityId(Page.class, page.getId()), pageDao.stringHashMapper.toHash(page));
    }

    private void saveSiteInstance(Transaction t, SiteInstance siteInstance) {
        t.hmset(entityId(SiteInstance.class, siteInstance.getId()),
                siteInstanceDao.stringHashMapper.toHash(siteInstance));
    }

    private SiteInstance prepareForkSiteInstance(SiteInstance designSiteInstance) {
        SiteInstance siteInstance = new SiteInstance();
        Date now = new Date();
        siteInstance.setCreatedAt(now);
        siteInstance.setUpdatedAt(now);
        siteInstance.setName(designSiteInstance.getName());
        siteInstance.setSiteId(designSiteInstance.getSiteId());
        siteInstance.setStatus(SiteInstance.Status.NORMAL.toNumber());
        siteInstance.setStyle(designSiteInstance.getStyle());
        long newSiteInstanceId = siteInstanceDao.newId();
        siteInstance.setId(newSiteInstanceId);
        return siteInstance;
    }

    private Map<Long,Long> idMap(List<Page> sourcePages) {
        Map<Long,Long> pageIdMapping = Maps.newHashMapWithExpectedSize(sourcePages.size());
        for (Page page : sourcePages) {
            Long newPageId = pageDao.newId();
            pageIdMapping.put(page.getId(), newPageId);
        }
        return pageIdMapping;
    }

    private List<Page> prepareForkPages(Long siteInstanceId,List<Page> sourcePages,Map<Long,Long> idMapping){
        for (Page sourcePage : sourcePages) {
            sourcePage.setId(idMapping.get(sourcePage.getId()));
            sourcePage.setSiteInstanceId(siteInstanceId);
        }
        return sourcePages;
    }


    /**
     * save new siteInstance and build index
     * @param t redis connection
     * @param newSiteInstance new site instance
     * @param headerWidgets   header widgets
     * @param footerWidgets   footer widgets
     * @param pages           pages
     * @param widgetsOfPages  page widgets
     */
    private void doSave(Transaction t, SiteInstance newSiteInstance, Iterable<Widget> headerWidgets,
                        Iterable<Widget> footerWidgets, List<Page> pages, Map<Long, Iterable<Widget>> widgetsOfPages) {
        saveSiteInstance(t, newSiteInstance);
        //add to site index
        Long siteInstanceId = newSiteInstance.getId();
        t.sadd(siteInstances(newSiteInstance.getSiteId()), siteInstanceId.toString());

        //save header widgets and index
        for (Widget headerWidget : headerWidgets) {
            saveWidget(t, headerWidget);
            t.sadd(headerWidgets(siteInstanceId), headerWidget.getId().toString());
        }

        //save footer widgets and index
        for (Widget footerWidget : footerWidgets) {
            saveWidget(t, footerWidget);
            t.sadd(footerWidgets(siteInstanceId), footerWidget.getId().toString());
        }

        //save pages and widgets
        for (Page page : pages) {
            savePage(t, page);
            //add to siteInstance index
            Long pageId = page.getId();
            t.sadd(pages(siteInstanceId), pageId.toString());

            if (Objects.equal("index", page.getPath())) {//set defaultPageId of siteInstance
                t.hset(entityId(SiteInstance.class, siteInstanceId), "defaultPageId",
                        pageId.toString());
            }

            Iterable<Widget> pageWidgets = widgetsOfPages.get(pageId);
            if (pageWidgets != null) {
                for (Widget pageWidget : pageWidgets) {
                    Long widgetId = pageWidget.getId();
                    saveWidget(t, pageWidget);

                    //add to page index
                    t.sadd(widgets(pageId),widgetId.toString());
                }
            }
        }
    }

    public void deleteSite(final Long siteId) {
        final Site site = siteDao.findById(siteId);
        if (siteId == null) {
            return;
        }
        siteDao.delete(siteId);
        if (site.getDefaultSiteInstanceId() != null) {
            deleteSiteInstance(siteId, site.getDefaultSiteInstanceId());
        }
        if (site.getReleaseSiteInstanceId() != null) {
            deleteSiteInstance(siteId, site.getReleaseSiteInstanceId());
        }
    }

    public void deleteSiteInstance(final Long siteId, final Long siteInstanceId) {
        final Set<String> pageIds = pageIdsOf(siteInstanceId);
        final Iterable<String> siteLevelWidgetIds = Iterables.concat(headerWidgetIdsOf(siteInstanceId), footerWidgetIdsOf(siteInstanceId));
        final Map<String, Iterable<String>> pageWidgetIds = Maps.newHashMapWithExpectedSize(Iterables.size(pageIds));
        for (String pageId : pageIds) {
            pageWidgetIds.put(pageId, widgetIdsOf(Long.parseLong(pageId)));
        }
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                doDeleteSiteInstance(t, siteId, siteInstanceId, pageIds, siteLevelWidgetIds, pageWidgetIds);
                t.exec();
            }
        });
    }

    //for site dao to reuse the connection to ensure transaction
    private void doDeleteSiteInstance(Transaction t, final Long siteId, final Long siteInstanceId, Iterable<String> pageIds,
                                      Iterable<String> siteWidgetIds, Map<String, Iterable<String>> pageWidgetIds) {

        t.del(entityId(SiteInstance.class, siteInstanceId));

        //remove form site index
        t.srem(siteInstances(siteId), siteInstanceId.toString());

        //remove site level widgets and its index
        deleteWidgets(t, siteWidgetIds);
        t.del(headerWidgets(siteInstanceId), footerWidgets(siteInstanceId),siteWidgets(siteInstanceId));

        //remove pages and its index
        for (String pageId : pageIds) {
            deletePage(t, pageId, pageWidgetIds.get(pageId));
        }
        t.del(pages(siteInstanceId));

        //delete page index
        t.del(rawKey(pages(siteInstanceId)));
    }

    private void deletePage(Transaction t, String pageId, Iterable<String> widgetIdsOfPage) {
        t.del(entityId(Page.class, pageId));
        deleteWidgets(t, widgetIdsOfPage);
        //remove page widget index
        t.del(widgets(Long.parseLong(pageId)));
    }

    //fork widgets,把widget的belongId改掉,生成一个新的Id并对应修改parent关联
    private Iterable<Widget> prepareForkWidgets(Long belongId, List<Widget> sourceWidgets) {
        Map<Long, Long> idMapping = Maps.newHashMapWithExpectedSize(sourceWidgets.size());

        for (Widget widget : sourceWidgets) {
            Long sourceId = widget.getId();

            long newWidgetId = widgetDao.newId();
            idMapping.put(sourceId, newWidgetId);
            widget.setId(newWidgetId);//replace id
            widget.setBelongId(belongId); //replace belongId
        }
        for (Widget newWidget : sourceWidgets) {
            newWidget.setParentId(idMapping.get(newWidget.getParentId()));//replace parentId
        }
        return sourceWidgets;
    }

    private SiteInstance designSiteInstanceOf(Site site) {

        Long designSiteInstanceId = site.getDefaultSiteInstanceId();
        return siteInstanceDao.findById(designSiteInstanceId);
    }


    private List<Page> pagesOf(Long siteInstanceId) {
        return pageDao.findBySiteInstanceId(siteInstanceId);
    }

    private List<Widget> headerWidgetsOf(Long siteInstanceId) {
        return widgetDao.siteLevelWidgets(Widget.BelongType.HEADER, siteInstanceId);
    }

    private List<Widget> footerWidgetsOf(Long siteInstanceId) {
        return widgetDao.siteLevelWidgets(Widget.BelongType.FOOTER, siteInstanceId);
    }

    private List<Widget> pageWidgetsOf(Long pageId) {
        return widgetDao.pageWidgets(pageId);
    }

    public void release(final Site site) {
        final Site siteInDB = siteDao.findById(site.getId());
        if(siteInDB == null){
            throw new IllegalArgumentException("can not find site with whose id="+site.getId());
        }
        final Long originalReleaseSiteInstanceId = siteInDB.getReleaseSiteInstanceId();
        final Long originalDesignSiteInstanceId = siteInDB.getDefaultSiteInstanceId();
        final SiteInstance designSiteInstance = designSiteInstanceOf(siteInDB);
        if (designSiteInstance != null) { //release is just fork from design site instance
            final SiteInstance releaseSiteInstance = prepareForkSiteInstance(designSiteInstance);

            final Long releaseSiteInstanceId = releaseSiteInstance.getId();

            List<Page> sourcePages = pagesOf(originalDesignSiteInstanceId);
            Map<Long,Long> idMapping = idMap(sourcePages);
            final Map<Long, Iterable<Widget>> pageWidgets = Maps.newHashMapWithExpectedSize(sourcePages.size());
            for (Page page : sourcePages) {
                Long newPageId = idMapping.get(page.getId());
                pageWidgets.put(newPageId, prepareForkWidgets(newPageId,pageWidgetsOf(page.getId())));
            }
            final List<Page> pages = prepareForkPages(releaseSiteInstanceId, sourcePages,idMapping);
            final Iterable<Widget> headerWidgets = prepareForkWidgets(releaseSiteInstanceId, headerWidgetsOf(originalDesignSiteInstanceId));
            final Iterable<Widget> footerWidgets = prepareForkWidgets(releaseSiteInstanceId, footerWidgetsOf(originalDesignSiteInstanceId));

            template.execute(new JedisTemplate.JedisActionNoResult() {
                @Override
                public void action(Jedis jedis) {
                    Transaction t = jedis.multi();
                    try {
                        doSave(t,releaseSiteInstance,headerWidgets,footerWidgets,pages,pageWidgets);
                        //set new release site instance id  and update status to release
                        site.setReleaseSiteInstanceId(releaseSiteInstanceId);
                        site.setStatus(Site.Status.NORMAL.toNumber());
                        t.hmset(entityId(Site.class, site.getId()),siteDao.stringHashMapper.toHash(site));
                        t.exec();
                    } catch (Exception e) {
                        t.discard();
                        Throwables.propagate(e);
                    }
                }
            });
        }

        //then remove old release siteInstance Id
        if (originalReleaseSiteInstanceId != null) {
            deleteSiteInstance(site.getId(), originalReleaseSiteInstanceId);
        }
    }

    /**
     * fork site
     * @param site   source site
     * @param userId  user id
     * @return  new forked site
     */
    public Site fork(final Site site, final Long userId) {
        //优先fork发布后的siteInstance,其次是装修中的siteInstance
        SiteInstance sourceSiteInstance = site.getReleaseSiteInstanceId()!=null?
                siteInstanceDao.findById(site.getReleaseSiteInstanceId()): siteInstanceDao.findById(site.getDefaultSiteInstanceId());

        final Site forkSite = prepareForkSite(site, userId);

        final SiteInstance siteInstance = prepareForkSiteInstance(sourceSiteInstance);

        siteInstance.setSiteId(forkSite.getId());
        forkSite.setDefaultSiteInstanceId(siteInstance.getId());


        final Long forkSiteInstanceId = siteInstance.getId();

        final Iterable<Widget> headerWidgets = prepareForkWidgets(forkSiteInstanceId, headerWidgetsOf(sourceSiteInstance.getId()));
        final Iterable<Widget> footerWidgets = prepareForkWidgets(forkSiteInstanceId, footerWidgetsOf(sourceSiteInstance.getId()));


        List<Page> sourcePages = pagesOf(sourceSiteInstance.getId());
        Map<Long,Long> idMapping = idMap(sourcePages);
        final Map<Long, Iterable<Widget>> pageWidgets = Maps.newHashMapWithExpectedSize(sourcePages.size());
        for (Page page : sourcePages) {
            Long newPageId = idMapping.get(page.getId());
            pageWidgets.put(newPageId, prepareForkWidgets(newPageId,pageWidgetsOf(page.getId())));
        }
        final List<Page> pages = prepareForkPages(forkSiteInstanceId, sourcePages,idMapping);
        template.execute(new JedisTemplate.JedisActionNoResult() {
            @Override
            public void action(Jedis jedis) {
                Transaction t = jedis.multi();
                try {
                    doSave(t, siteInstance, headerWidgets, footerWidgets, pages, pageWidgets);
                    //save fork site
                    t.hmset(entityId(Site.class, forkSite.getId()), siteDao.stringHashMapper.toHash(forkSite));

                    //add subdomain index
                    String forkId = forkSite.getId().toString();
                    t.setnx(subDomain(forkSite.getSubDomain()),forkId);

                    //add to all site index
                    t.lpush(allSites(), forkId);
                    t.exec();
                } catch (Exception e) {
                    t.discard();
                    Throwables.propagate(e);
                }
            }
        });
        return forkSite;
    }

    private Site prepareForkSite(Site site, Long userId) {
        final Site forkSite = new Site();
        forkSite.setForkFrom(site.getId());
        //set fork root
        forkSite.setForkRoot(site.getForkRoot() == null ? site.getId() : site.getForkRoot());
        //default name
        forkSite.setName("新站点(From:" + site.getName() + ")");
        forkSite.setStatus(Site.Status.DESIGN.toNumber());
        forkSite.setUserId(userId);
        forkSite.setId(siteDao.newId());
        forkSite.setForks(0);
        forkSite.setStars(0);
        forkSite.setForkable(-1);//已fork的site默认不能被fork
        forkSite.setSubDomain(forkSite.getId()+"." + commonConstants.getDomain());
        forkSite.setImage(site.getImage());
        Date now = new Date();
        forkSite.setCreatedAt(now);
        forkSite.setUpdatedAt(now);
        return forkSite;
    }

}
