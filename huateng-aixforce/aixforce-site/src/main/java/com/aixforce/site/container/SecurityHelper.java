/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.container;

import com.aixforce.site.container.exception.UnAuthorize401Exception;

/**
 * Desc:  组件权限校验
 * Author: dimzfw@gmail.com
 * Date: 10/31/12 11:32 AM
 */
public interface SecurityHelper {
    public static enum ResourceType {Site, SiteInstance, Page, Widget, Item}

    void checkPermission(final Long resourceId, final ResourceType resourceType)
            throws UnAuthorize401Exception;

    /**
     * 根据资源Id和资源类型获取对应的UserId
     *
     * @param resourceId   资源ID
     * @param resourceType 资源类型
     * @return 所属站点的Id ,没找到返回－100L
     */
    Long getSiteOwner(final Long resourceId, final ResourceType resourceType);

}