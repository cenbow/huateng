/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.common.model.Paging;
import com.aixforce.exception.ServiceException;
import com.aixforce.site.dao.mysql.UserImageDao;
import com.aixforce.site.model.UserImage;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-21
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {
    private final static Logger log = LoggerFactory.getLogger(ImageServiceImpl.class);

    @Autowired
    private UserImageDao userImageDao;

    @Autowired
    private QuotaService quotaService;

    @Override
    public void addUserImage(UserImage userImage) {
        checkArgument(userImage.getUserId() != null, "userId can not be null");
        checkArgument(userImage.getFileName() != null, "image file name can not be null");
        checkArgument(userImage.getFileSize() != null, "image file size can not be null");
        try {
            userImageDao.create(userImage);
            log.debug("succeed to create {}", userImage);

            quotaService.updateUsedImageInfo(userImage.getUserId(),1,userImage.getFileSize());
        } catch (Exception e) {
            log.error("failed to create {},cause:{}", userImage, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public UserImage findUserImageById(Long imageId) {
        checkNotNull(imageId);
        return userImageDao.findById(imageId);
    }

    @Override
    public void deleteUserImage(UserImage userImage) {
        checkNotNull(userImage, "imageId can not be null");
        try {
            userImageDao.delete(userImage.getId());
            quotaService.updateUsedImageInfo(userImage.getUserId(), -1, -userImage.getFileSize());
        } catch (Exception e) {
            log.error("failed to delete {},cause:{}", userImage, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Paging<UserImage> findUserImagesByUserId(Long userId, Integer offset, Integer limit) {
        checkArgument(userId != null, "userId can not be null");
        checkArgument(offset != null, "offset can not be null");
        checkArgument(limit != null, "limit can not be null");
        try {
            return userImageDao.findByUserId(userId, offset, limit);
        } catch (Exception e) {
            log.error("failed to find UserImages by user_id={} limit {},{},cause:{}",
                   userId, offset, limit, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public Integer countOfImages(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        try {
            return userImageDao.countOf(userId);
        } catch (Exception e) {
            log.error("failed to find total image count of user(id={}),cause:{}", userId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    @Override
    public Long totalImageSize(Long userId) {
        checkArgument(userId != null, "userId can not be null");
        try {
            return quotaService.getTotalImageSize(userId);
        } catch (Exception e) {
            log.error("failed to find total image size of user(id={}),cause:{}", userId, Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }

    /**
     * 删除用户对应的上传记录
     *
     * @param userId 用户id
     */
    @Override
    public void deleteByUserId(Long userId) {
        checkArgument(userId!=null,"userId can not be null");
        try{
            quotaService.deleteByUserId(userId);
        }catch (Exception e){
            log.error("failed to delete userImage of user(id={}),cause:{}",userId,Throwables.getStackTraceAsString(Throwables.getRootCause(e)));
            throw new ServiceException(e);
        }
    }


}
