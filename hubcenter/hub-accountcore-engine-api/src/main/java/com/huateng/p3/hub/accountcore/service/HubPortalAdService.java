package com.huateng.p3.hub.accountcore.service;

import com.aixforce.annotations.ParamInfo;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.models.AnnouncementVo;

import java.util.List;

/**
 * 公告 接口
 * User: 董培基
 * Date: 14-11-25
 * Time: 下午4:20
 */
public interface HubPortalAdService {

    /**
     * 获取公告列表
     * @return  公告列表几何
     */
    public Response<Paging<AnnouncementVo>> getAllEnAbleAnnounce(@ParamInfo("startIndex")Integer startIndex, @ParamInfo("pageSize")Integer pageSize);

    /**
     * 获取公告详情
     * @param id  公告id
     * @return 公告
     */
    public Response<AnnouncementVo> findAnnouncementVoById(@ParamInfo("id")String id);
}
