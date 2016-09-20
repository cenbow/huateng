/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.common.model.Paging;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.user.base.BaseUser;

import java.util.List;
import java.util.Map;

/**
 * Desc:  Site crud
 * Author: dimzfw@gmail.com
 * Date: 8/17/12 6:40 PM
 */
public interface SiteService {

    /**
     * If site has id execute update,else create.
     *
     * @param userId current user
     * @param site site data
     * @return site's id
     */
    Long create(Long userId, Site site);

    void delete(Long userId,Long siteId);

    /**
     * 对应的SiteInstance没有页面部分
     *
     * @param userId current user
     * @param site site
     * @return site Instance
     */
    SiteInstance createWithoutPagePart(Long userId, Site site);

    void update(Site site);

    /**
     * 发布站点
     *
     * @param site 站点
     */
    void release(Site site);

    /**
     * find Site by domain
     *
     * @param domain top domain or sub domain
     * @return Site
     */
    Site findByDomain(String domain);

    List<Site> findByUser(@ParamInfo("user")BaseUser user);

    List<Site> findByUserId(Long userId);

    /**
     * 查找用户的店铺,一个用户只允许有一个店铺
     * @param userId 用户id
     * @return 店铺
     */
    Site findShopByUserId(Long userId);

    void setDefault(Long siteId, Long siteInstanceId);

    Site findById(Long siteId);

    Map<String,Object> findBySite(@ParamInfo("site")Site site);

    /**
     * 切换站点所有者id
     * @param baseUser 系统自动注入id
     * @param siteId  站点id
     * @param ownerId 新的所有者id
     */
    void changeOwner(@ParamInfo("baseUser")BaseUser baseUser, @ParamInfo("siteId")Long siteId,@ParamInfo("ownerId") Long ownerId);

    /**
     * 分页查询所有站点
     * @param pageNo 起始页码
     * @param size   每页返回条数
     * @return  分页结果
     */
    Paging<Site> pagination(@ParamInfo("pageNo")Integer pageNo, @ParamInfo("size")Integer size);
}
