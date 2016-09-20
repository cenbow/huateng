package com.aixforce.site.service;

import com.aixforce.site.dao.mysql.UserQuotaDao;
import com.aixforce.site.model.UserQuota;
import com.google.common.base.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkArgument;

/*
* Author: jlchen
* Date: 2012-12-04
*/
@Service
public class QuotaServiceImpl implements QuotaService {
    private static final Logger log = LoggerFactory.getLogger(QuotaServiceImpl.class);

    @Autowired
    private UserQuotaDao quotaDao;

    @Value("#{app.defaultMaxImageCount}")
    @Autowired(required = false)
    private Integer defaultMaxImageCount = 100;

    @Value("#{app.defaultMaxImageSize}")
    @Autowired(required = false)
    private Long defaultMaxImageSize = 100 * 1024 * 1024L;

    @Value("#{app.defaultMaxWidgetCount}")
    @Autowired(required = false)
    private Integer defaultMaxWidgetCount = 1000;


    /**
     * 创建一个quota
     *
     * @param userQuota userQuota
     */
    @Override
    public void create(UserQuota userQuota) {
        checkArgument(userQuota.getUserId() != null, "userId can not be null");
        if (userQuota.getMaxImageCount() == null) {
            userQuota.setMaxImageCount(defaultMaxImageCount);
        }
        if (userQuota.getMaxImageSize() == null) {
            userQuota.setMaxImageSize(defaultMaxImageSize);
        }
        if (userQuota.getMaxWidgetCount() == null) {
            userQuota.setMaxWidgetCount(defaultMaxWidgetCount);
        }
        quotaDao.create(userQuota);
    }

    /**
     * 根据userId查找quota
     *
     * @param userId 用户id
     * @return 配额情况
     */
    @Override
    public UserQuota findByUserId(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        UserQuota userQuota = quotaDao.findByUserId(userId);
        if (userQuota == null) {//先创建
            userQuota = new UserQuota();
            userQuota.setUserId(userId);
            create(userQuota);
            calculateUsedImageInfo(userId);
            calculateWidgetCount(userId);
            return quotaDao.findByUserId(userId);
        } else {
            return userQuota;
        }
    }

    /**
     * 更新quota的信息,主要是用来增大配额
     *
     * @param userQuota userQuota
     */
    @Override
    public void update(UserQuota userQuota) {
        checkArgument(userQuota.getUserId() != null, "userId can not be null");
        quotaDao.update(userQuota);
    }

    /**
     * 删除用户的quota设置
     *
     * @param userId 用户id
     */
    @Override
    public void deleteByUserId(Long userId) {
        checkArgument(userId!=null,"userId can not be null");
        quotaDao.deleteByUserId(userId);
    }


    /**
     * 获取用户已上传图片的个数
     *
     * @param userId 用户id
     * @return 图片个数
     */
    @Override
    public Integer getTotalImageCount(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        UserQuota userQuota = quotaDao.findByUserId(userId);
        if (userQuota == null) {
            log.error("calculate quota for user(id={}) failed", userId);
            return 0;
        }
        return userQuota.getUsedImageCount();
    }

    /**
     * 获取用户所有已上传图片的大小
     *
     * @param userId 用户id
     * @return 总的图片大小, 以byte为单位
     */
    @Override
    public Long getTotalImageSize(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        UserQuota userQuota = quotaDao.findByUserId(userId);
        if (userQuota == null) {
            log.error("calculate quota for user(id={}) failed", userId);
            return 0L;
        }
        return userQuota.getUsedImageSize();
    }

    /**
     * 获取用户所有站点消耗的widget个数
     *
     * @param userId 用户id
     * @return widget数目
     */
    @Override
    public Integer getTotalWidgetCount(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        UserQuota userQuota = quotaDao.findByUserId(userId);
        if (userQuota == null) {
            log.error("calculate quota for user(id={}) failed", userId);
            return 0;
        }
        return userQuota.getUsedWidgetCount();
    }

    /**
     * 计算用户所使用的widget的个数
     *
     * @param userId 用户id
     */
    @Override
    public void calculateWidgetCount(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        quotaDao.calculateWidgetCount(userId);
    }

    /**
     * 计算用户所使用的图片大小和数目
     *
     * @param userId 用户id
     */
    @Override
    public void calculateUsedImageInfo(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        quotaDao.calculateUsedImageInfo(userId);
    }

    /**
     * 增加或者减少用户使用的图片消耗信息
     *
     * @param userId     用户id
     * @param deltaCount 图片数目变化,可以为负
     * @param deltaSize  图片大小变化,可以为负
     */
    @Override
    public void updateUsedImageInfo(Long userId, Integer deltaCount, Integer deltaSize) {
        checkArgument(userId != null, "userId can not be null");
        checkArgument(deltaCount != null, "deltaCount can not be null");
        checkArgument(deltaSize != null, "deltaSize can not be null");
        quotaDao.updateUsedImageInfo(userId, deltaCount, deltaSize);
    }

    /**
     * 更新用户所使用的widget个数
     *
     * @param userId     用户id
     * @param deltaCount 变化数目个数,可正可负
     */
    @Override
    public void updateUsedWidgetCount(Long userId, Integer deltaCount) {
        checkArgument(userId != null, "userId can not be null");
        checkArgument(deltaCount != null, "widgetCount delta can not be null");
        quotaDao.updateUsedWidgetCount(userId, deltaCount);
    }

    /**
     * 检查用户的图片配额是否超过
     *
     * @param userId 用户id
     * @return 是否超额
     */
    @Override
    public boolean imageOverQuota(Long userId) {
        UserQuota userQuota = findByUserId(userId);
        int imageCount = Objects.firstNonNull(userQuota.getUsedImageCount(), 0);
        long imageSize = Objects.firstNonNull(userQuota.getUsedImageSize(), 0L);
        return imageCount >= Objects.firstNonNull(userQuota.getMaxImageCount(),defaultMaxImageCount)
                || imageSize >= Objects.firstNonNull(userQuota.getMaxImageSize(),defaultMaxImageSize);
    }

    /**
     * 检查用户的widget配额是否超过
     *
     * @param userId 用户id
     * @return 是否超额
     */
    @Override
    public boolean widgetOverQuota(Long userId) {
        UserQuota userQuota = findByUserId(userId);
        int widgetCount = Objects.firstNonNull(userQuota.getUsedWidgetCount(),0);
        return widgetCount>= Objects.firstNonNull(userQuota.getMaxWidgetCount(),defaultMaxWidgetCount);
    }
}
