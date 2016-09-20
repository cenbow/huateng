package com.aixforce.site.service;

import com.aixforce.site.model.UserQuota;

/*
* Author: jlchen
* Date: 2012-12-04
*/
public interface QuotaService {
    /**
     * 检查用户上传图片的数目及大小是否超出配额
     * @param userId 用户id
     */
    //void imageQuota(Long userId);

    /**
     * 检查用户拥有的widget的数目是否超出配额
     * @param userId 用户id
     */
    //void widgetQuota(Long userId);

    /**
     * 创建一个quota
     * @param userQuota userQuota
     */
    void create(UserQuota userQuota);

    /**
     * 根据userId查找quota
     * @param userId  用户id
     * @return 配额情况
     */
    UserQuota findByUserId(Long userId);

    /**
     * 更新quota的信息,主要是用来增大配额
     * @param userQuota userQuota
     */
    void update(UserQuota userQuota);

    /**
     * 删除用户的quota设置
     * @param userId 用户id
     */
    void deleteByUserId(Long userId);

    /**
     * 获取用户已上传图片的个数
     * @param userId 用户id
     * @return 图片个数
     */
    Integer getTotalImageCount(Long userId);

    /**
     * 获取用户所有已上传图片的大小
     * @param userId 用户id
     * @return 总的图片大小,以byte为单位
     */
    Long getTotalImageSize(Long userId);

    /**
     * 获取用户所有站点消耗的widget个数
     * @param userId 用户id
     * @return widget数目
     */
    Integer getTotalWidgetCount(Long userId);

    /**
     * 计算用户所使用的widget的个数
     * @param userId 用户id
     */
    void calculateWidgetCount(Long userId);

    /**
     * 计算用户所使用的图片大小和数目
     * @param userId 用户id
     */
    void calculateUsedImageInfo(Long userId);

    /**
     * 增加或者减少用户使用的图片消耗信息
     *
     * @param userId          用户id
     * @param deltaCount 图片数目变化,可以为负
     * @param deltaSize  图片大小变化,可以为负
     */
    void updateUsedImageInfo(Long userId, Integer deltaCount, Integer deltaSize);

    /**
     * 更新用户所使用的widget个数
     *
     * @param userId           用户id
     * @param deltaCount 变化数目个数,可正可负
     */
    void updateUsedWidgetCount(Long userId, Integer deltaCount);

    /**
     * 检查用户的图片配额是否超过
     * @param userId 用户id
     * @return 是否超额
     */
    boolean imageOverQuota(Long userId);

    /**
     * 检查用户的widget配额是否超过
     * @param userId 用户id
     * @return 是否超额
     */
    boolean widgetOverQuota(Long userId);
}
