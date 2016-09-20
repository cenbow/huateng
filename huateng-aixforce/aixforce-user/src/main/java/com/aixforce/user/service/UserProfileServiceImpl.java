package com.aixforce.user.service;

import com.aixforce.exception.ServiceException;
import com.aixforce.user.model.UserProfile;
import com.aixforce.user.mysql.UserProfileDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-17
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final static Logger logger = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    @Autowired
    private UserProfileDao userProfileDao;

    public void createUserProfile(UserProfile userProfile) {
        checkArgument(userProfile.getUserId() != null, "userId should not be null when created");
        try {
            userProfileDao.create(userProfile);
        } catch (Exception e) {
            logger.error("failed to create {},cause:{}", userProfile, e);
            throw new ServiceException(e);
        }
    }

    @Override
    @Nullable
    public UserProfile findUserProfileByUserId(Long userId) {
        try {
            return userProfileDao.findByUserId(userId);
        } catch (Exception e) {
            logger.error("failed to find userProfile by userId={},cause:{}", userId, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateUserProfileByUserId(UserProfile userProfile) {
        try {
            UserProfile up = userProfileDao.findByUserId(userProfile.getUserId());
            if (up == null) {
                createUserProfile(userProfile);
            } else {
                userProfileDao.updateByUserId(userProfile);
            }
        } catch (Exception e) {
            logger.error("failed to update {},cause:{}", userProfile, e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUserProfileByUserId(Long userId) {
        try {
            userProfileDao.deleteByUserId(userId);
        } catch (Exception e) {
            logger.error("failed to delete userProfile by userId={},cause:{}", userId, e);
            throw new ServiceException(e);
        }
    }
}
