/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;

import com.aixforce.common.model.Paging;
import com.aixforce.site.model.UserImage;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-09-21
 */
public interface ImageService {

    void addUserImage(UserImage userImage);

    Paging<UserImage> findUserImagesByUserId(Long userId,Integer offset,Integer limit);

    /**
     * find image count of user
     * @param userId user id
     * @return image count
     */
    Integer countOfImages(Long userId);

    /**
     * compute total image size of user
     * @param userId  user id
     * @return  total image size
     */
    Long totalImageSize(Long userId);

    /**
     * 删除用户对应的上传记录
     * @param userId 用户id
     */
    void deleteByUserId(Long userId);

    /**
     * 删除一个用户图片
     * @param userImage 用户图片
     */
    void deleteUserImage(UserImage userImage);

    /**
     * 通过id获取一个用户图片
     * @param imageId 用户图片id
     * @return 用户图片
     */
    UserImage findUserImageById(Long imageId);
}
