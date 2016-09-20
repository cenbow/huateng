package com.aixforce.user.service;

import com.aixforce.user.model.UserProfile;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-07-17
 */
public interface UserProfileService {

    void createUserProfile(UserProfile userProfile);

    UserProfile findUserProfileByUserId(Long userId);

    void updateUserProfileByUserId(UserProfile userProfile);

    void deleteUserProfileByUserId(Long userId);
}
