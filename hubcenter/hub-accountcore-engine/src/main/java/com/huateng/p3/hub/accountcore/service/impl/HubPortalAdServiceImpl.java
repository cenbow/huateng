package com.huateng.p3.hub.accountcore.service.impl;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;
import com.huateng.p3.account.common.enummodel.Paging;
import com.huateng.p3.component.Response;
import com.huateng.p3.hub.accountcore.mapper.TPortalAdMapper;
import com.huateng.p3.hub.accountcore.models.AnnouncementVo;
import com.huateng.p3.hub.accountcore.service.HubPortalAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-11-25
 * Time: 下午4:36
 */
@Service
public class HubPortalAdServiceImpl implements HubPortalAdService {

   
    @Autowired
    private TPortalAdMapper tPortalAdMapper;

    @Override
    public Response<Paging<AnnouncementVo>> getAllEnAbleAnnounce(Integer startIndex, Integer pageSize) {
        startIndex = Objects.firstNonNull(startIndex, 1);
        pageSize = Objects.firstNonNull(pageSize, 10); //默认10
        startIndex = (startIndex - 1) * pageSize;
        Integer endIndex = startIndex + pageSize;
        List<AnnouncementVo> announcementVos =  tPortalAdMapper.getAllEnAbleAnnounce(ImmutableMap.of("startIndex",startIndex,"endIndex",endIndex));
        Long total = tPortalAdMapper.findCountAllEnAbleAnnounce();
        Paging<AnnouncementVo> paging = new Paging<AnnouncementVo>(total, announcementVos);
        return new Response<Paging<AnnouncementVo>>(paging);
    }

    @Override
    public Response<AnnouncementVo> findAnnouncementVoById(String id) {
        AnnouncementVo announcementVo = tPortalAdMapper.findAnnouncementVoById(id);
        return new Response<AnnouncementVo>(announcementVo);
    }
}
