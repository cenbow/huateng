/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.exception.ServiceException;
import com.aixforce.site.dao.redis.SiteManager;
import com.aixforce.site.model.redis.Site;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 11/10/12 5:45 PM
 */
@Service("siteForkService")
public class SiteForkServiceImpl implements SiteForkService {

    private final static Logger logger = LoggerFactory.getLogger(SiteForkServiceImpl.class);

    @Autowired
    private SiteManager siteManager;

    @Autowired
    private SiteService siteService;


    @Override
    public Long fork(Long userId, Long siteId) {
        checkNotNull(userId, "userId can not be null.");
        //1.site and siteInstance
        Site sourceSite = siteService.findById(siteId);
        checkNotNull(sourceSite, "source site can not be null.");

        if (!Objects.equal(sourceSite.getForkable(), 1)) {
            throw new ServiceException("not forkable site(id=" + siteId + ")");
        }
        Site forkSite = siteManager.fork(sourceSite, userId);

        //4.forks++
        Site updateSourceSite = new Site();
        updateSourceSite.setId(sourceSite.getId());
        updateSourceSite.setForks(sourceSite.getForks() == null ? 1 : sourceSite.getForks() + 1);
        siteService.update(updateSourceSite);

        logger.info("Fork site [{}] from [{}] successfully.",
                forkSite,
                        Objects.firstNonNull(sourceSite.getDomain(), sourceSite.getSubDomain())
                );

        return forkSite.getId();
    }
}