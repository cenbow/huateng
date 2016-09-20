package com.huateng.p3.hub.accountcore.mapper;

import com.huateng.p3.hub.accountcore.models.AnnouncementVo;

import java.util.List;
import java.util.Map;

/**
 * User: 董培基
 * Date: 14-11-25
 * Time: 下午4:37
 */
public interface TPortalAdMapper {
    /**
     * 获取公告列表
     * @return  公告列表集合
     */
    public List<AnnouncementVo> getAllEnAbleAnnounce(Map map);

    /**
     * 查询所有的公告列表 (有效)
     * @return 数量
     */
    public Long findCountAllEnAbleAnnounce();

    /**
     * 获取公告详情
     * @param id  公告id
     * @return 公告
     */
    public AnnouncementVo findAnnouncementVoById(String id);
}
