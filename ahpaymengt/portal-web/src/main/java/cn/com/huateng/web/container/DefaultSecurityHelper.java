/*
 * Copyright (c) 2013 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.container;

import com.aixforce.site.container.SecurityHelper;
import com.aixforce.site.container.exception.NotFound404Exception;
import com.aixforce.site.container.exception.UnAuthorize401Exception;
import com.aixforce.site.model.redis.Page;
import com.aixforce.site.model.redis.Site;
import com.aixforce.site.model.redis.SiteInstance;
import com.aixforce.site.model.redis.Widget;
import com.aixforce.site.service.PageService;
import com.aixforce.site.service.SiteInstanceService;
import com.aixforce.site.service.SiteService;
import com.aixforce.site.service.WidgetService;
import com.aixforce.user.base.UserUtil;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author: dimzfw@gmail.com
 * Date: 10/31/12 11:32 AM
 */
@Component
public class DefaultSecurityHelper implements SecurityHelper {

    private final static Logger log = LoggerFactory.getLogger(DefaultSecurityHelper.class);

    @Autowired
    private SiteInstanceService siteInstanceService;

    @Autowired
    private PageService pageService;

    @Autowired
    private WidgetService widgetService;

    @Autowired
    private SiteService siteService;

    @Autowired(required = false)
    private UserFinder userFinder;

    public void checkPermission(final Long resourceId, final ResourceType resourceType)
            throws UnAuthorize401Exception {

        final Long userId = UserUtil.getUserId();
        Long siteOwner = getSiteOwner(resourceId, resourceType);
        if (!Objects.equal(userId, siteOwner)) {
            log.error("expect userId={},actual userId={}", siteOwner, userId);
            throw new UnAuthorize401Exception("authorization failed: userId-" + userId + ",siteOwner" + siteOwner + ",resourceId-" + resourceId + ",resourceType:" + resourceType);
        }
    }

    /**
     * 根据资源Id和资源类型获取对应的UserId
     *
     * @param resourceId   资源ID
     * @param resourceType 资源类型
     * @return 所属站点的Id ,没找到返回－100L
     */
    public Long getSiteOwner(final Long resourceId, final ResourceType resourceType) {

        try {
            if (resourceType == ResourceType.Site) {
                return findBySiteId(resourceId);
            }
            if (resourceType == ResourceType.SiteInstance) {
                return findBySiteInstanceId(resourceId);
            }
            if (resourceType == ResourceType.Page) {
                return findByPageId(resourceId);
            }
            if (resourceType == ResourceType.Widget) {
                Widget widget = widgetService.findById(resourceId);
                //in page
                if (Widget.BelongType.PAGE.toNumber().equals(widget.getBelongType())) {
                    return findByPageId(widget.getBelongId());
                } else if (Widget.BelongType.FOOTER.toNumber().equals(widget.getBelongType()) || Widget.BelongType.HEADER.toNumber().equals(widget.getBelongType())) {
                    return findBySiteInstanceId(widget.getBelongId());
                } else if (Widget.BelongType.ITEM.toNumber().equals(widget.getBelongType())) {
                    if (userFinder != null) {
                        return userFinder.getUserId(widget.getBelongId());
                    }
                }
            }
            if (resourceType == ResourceType.Item) {
                if (userFinder != null) {
                    return userFinder.getUserId(resourceId);
                }
            }

            log.error("failed to find user of resource(resourceType={},resourceId={}) ", resourceType, resourceId);

        } catch (Exception e) {
            Throwables.propagateIfInstanceOf(e, NotFound404Exception.class);
            log.error("failed to find user of resource(resourceType={},resourceId={}),cause:{} ",
                    resourceType, resourceId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
        }
        return -100L;
    }


    protected Long findByPageId(Long pageId) {
        Page page = pageService.findById(pageId);
        if (page == null) {
            log.error("failed to found page(id={})", pageId);
            throw new NotFound404Exception("page not found");
        }
        return findBySiteInstanceId(page.getSiteInstanceId());
    }

    protected Long findBySiteInstanceId(Long siteInstanceId) {
        SiteInstance siteInstance = siteInstanceService.findById(siteInstanceId);
        if (siteInstance == null) {
            log.error("failed to found siteInstance(id={})", siteInstanceId);
            throw new NotFound404Exception("siteInstance not found");
        }
        return findBySiteId(siteInstance.getSiteId());
    }

    protected Long findBySiteId(Long siteId) {
        Site site = siteService.findById(siteId);
        if (site == null) {
            log.error("failed to found site(id={})", siteId);
            throw new NotFound404Exception("site not found");
        }
        return site.getUserId();
    }
}