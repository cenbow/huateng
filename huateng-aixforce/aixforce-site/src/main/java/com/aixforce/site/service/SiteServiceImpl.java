/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.common.model.Paging;
import com.aixforce.common.utils.CommonConstants;
import com.aixforce.exception.ServiceException;
import com.aixforce.site.dao.redis.RedisSiteDao;
import com.aixforce.site.dao.redis.SiteManager;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.model.redis.SiteCategory;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.user.base.BaseUser;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Desc: Implement SiteService
 * Author: dimzfw@gmail.com
 * Date: 8/20/12 11:46 AM
 */
@Service("siteService")
public class SiteServiceImpl implements SiteService {
    private static Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private CommonConstants commonConstants;

    @Autowired
    private SiteManager siteManager;

    @Autowired
    private RedisSiteDao siteDao;

    @Autowired
    private SiteInstanceService siteInstanceService;

    @Autowired
    private SiteCategoryService siteCategoryService;


    private LoadingCache<Integer, Map<String, List<SiteCategory>>> cache;


    @PostConstruct
    public void init() {
        this.cache = CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.HOURS).maximumSize(1L)
                .build(new CacheLoader<Integer, Map<String, List<SiteCategory>>>() {
                    public Map<String, List<SiteCategory>> load(Integer key) throws Exception {
                        List<SiteCategory> siteCategories = siteCategoryService.all();
                        return siteCategoryService.group(siteCategories);
                    }
                });
    }

    @Override
    public Long create(Long userId, Site site) {
        return _create(userId, site, true).getSiteId();
    }

    @Override
    public SiteInstance createWithoutPagePart(Long userId, Site site) {
        return _create(userId, site, false);
    }

    private SiteInstance _create(Long userId, Site site, boolean withPagePart) {
        checkNotNull(userId, "userId can not be null");
        checkArgument(site.getId() == null);

        site.setUserId(userId);
        site.setType(Objects.firstNonNull(site.getType(), Site.Type.NORMAL.toNumber()));
        site.setStatus(Site.Status.DESIGN.toNumber());
        site.setName("新站点");
        site.setForks(0);
        site.setStars(0);
        site.setId(siteDao.newId());    //generate id.
        if (site.getForkable() == null) {
            site.setForkable(-1);//默认不能被fork
        }
        site.setSubDomain(site.getId() + "." + commonConstants.getDomain());    // set default sub domain

        //create siteInstance
        SiteInstance siteInstance = new SiteInstance();
        siteInstance.setSiteId(site.getId());
        siteInstanceService.create(siteInstance, withPagePart);

        site.setDefaultSiteInstanceId(siteInstance.getId());  //set default siteInstance
        //create site, if this site is template ,it will also add to designer's template index in dao
        siteDao.create(site);

        logger.debug("site created - [user:{},id:{},name:{},domain:{}]",
                userId, site.getId(), site.getName(), site.getDomain());
        return siteInstance;
    }

    @Override
    public void delete(Long userId, Long siteId) {
        checkNotNull(siteId, "SiteId can not be null");
        Site site = this.findById(siteId);
        //1.只能删除自己的站点
        checkArgument(Objects.equal(site.getUserId(), userId), "非自己的站点,不能删除");
        //2.主站不能删除
        checkArgument(!Objects.equal(commonConstants.getMainSite(), site.getDomain()), "主站不可删除");

        //3.开始删除
        siteManager.deleteSite(siteId);

        //3.4 fork--
        if (site.getForkRoot() != null && site.getForkRoot() > 0) {
            Site forkRootSite = siteDao.findById(site.getForkRoot());

            if (forkRootSite != null && forkRootSite.getForks() != null && forkRootSite.getForks() > 0) {
                Site update = new Site();
                update.setId(forkRootSite.getId());
                update.setForks(forkRootSite.getForks() - 1);
                siteDao.update(update);
            }
        }
    }

    @Override
    public void update(Site site) {
        checkNotNull(site.getId());
        /*if (!NameValidator.validate(site.getName())) {
            throw new IllegalArgumentException("站点名称只能由字母,数字和下划线组成");
        }*/
        Site siteInDB = siteDao.findById(site.getId());
        if (siteInDB == null) {
            throw new IllegalStateException("site not exist");
        }
        if (!Strings.isNullOrEmpty(site.getDomain()) && !Objects.equal(site.getDomain(), siteInDB.getDomain())) {
            siteDao.updateDomain(site.getId(), site.getDomain());
        }
        site.setDomain(null);
        if (!Strings.isNullOrEmpty(site.getSubDomain()) && !Objects.equal(site.getSubDomain(), siteInDB.getSubDomain())) {
            siteDao.updateSubDomain(site.getId(), site.getSubDomain());
        }
        site.setSubDomain(null);
        siteDao.update(site);
    }

    /**
     * 发布站点
     *
     * @param site 站点
     */
    @Override
    public void release(Site site) {
        Long siteId = site.getId();
        checkArgument(siteId != null, "siteId can not be null");
        try {
            siteManager.release(site);
        } catch (Exception e) {
            logger.error("release site {} failed, cause:{}", site, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Site findByDomain(String domain) {
        checkNotNull(domain, "domain can not be null!");

        //系统创建都写的是二级域名，默认是siteId,进来后修改
        boolean isSubDomain = !Objects.equal(commonConstants.getMainSite(), domain) && domain.endsWith(commonConstants.getDomain());

        return siteDao.findByDomain(domain, isSubDomain);
    }

    @Override
    public List<Site> findByUser(BaseUser user) {
        checkNotNull(user);
        return findByUserId(user.getId());
    }

    @Override
    public List<Site> findByUserId(Long userId) {
        checkArgument(userId != null, "user id can not be null");
        return siteDao.findByUserId(userId);
    }

    /**
     * 查找用户的店铺,一个用户只允许有一个店铺
     *
     * @param userId 用户id
     * @return 店铺
     */
    @Override
    public Site findShopByUserId(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        List<Site> sites = findByUserId(userId);
        Iterable<Site> shop = Iterables.filter(sites, new Predicate<Site>() {
            @Override
            public boolean apply(Site site) {
                return Objects.equal(site.getType(), Site.Type.SHOP.toNumber());
            }
        });
        int found = Iterables.size(shop);
        if (found == 0) {
            logger.warn("no shop found for user(id={})", userId);
            throw new ServiceException("没有找到用户的店铺");
        } else if (found > 1) {
            logger.error("expect only 1 shop ,but found :{}", found);
            throw new ServiceException("用户有多个店铺");
        }
        return Iterables.get(shop, 0);
    }

    @Override
    public void setDefault(Long siteId, Long siteInstanceId) {
        checkNotNull(siteId);
        checkNotNull(siteInstanceId);

        Site site = new Site();
        site.setId(siteId);
        site.setDefaultSiteInstanceId(siteInstanceId);
        siteDao.update(site);
    }

    @Override
    public Site findById(Long siteId) {
        return siteDao.findById(siteId);
    }

    @Override
    public Map<String, Object> findBySite(Site site) {
        checkArgument(site.getId() != null, "siteId can not be null");
        Site siteInDB = findById(site.getId());
        if (siteInDB == null) {
            logger.error("failed to find site with id={}", site.getId());
            return ImmutableMap.<String, Object>of("categories", cache.getUnchecked(1).entrySet());
        }
        return ImmutableMap.of("site", siteInDB, "categories", cache.getUnchecked(1).entrySet());
    }

    /**
     * 切换站点所有者id
     *
     * @param baseUser 系统自动注入id
     * @param siteId   站点id
     * @param ownerId  新的所有者id
     */
    @Override
    public void changeOwner(BaseUser baseUser, Long siteId, Long ownerId) {
        if (BaseUser.TYPE.ADMIN != baseUser.getTypeEnum()) {
            logger.error("only admin can change site owner");
            throw new ServiceException("only admin can change site owner");
        }
        Site site = siteDao.findById(siteId);
        if (site == null) {
            logger.error("failed to find site by id={}", siteId);
            throw new IllegalArgumentException("failed to find specified site");
        }
        siteDao.changeOwner(siteId, site.getUserId(), ownerId);
    }

    /**
     * 分页查询所有站点
     *
     * @param pageNo 起始页码
     * @param size   每页返回条数
     * @return 分页结果
     */
    @Override
    public Paging<Site> pagination(Integer pageNo, Integer size) {
        return siteDao.pagination((pageNo -1)*size,size);
    }
}
