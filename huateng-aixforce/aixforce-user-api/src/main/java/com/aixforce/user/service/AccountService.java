/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.user.service;

import com.aixforce.annotations.ParamInfo;
import com.aixforce.common.model.Paging;
import com.aixforce.user.model.User;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2012-08-14
 */
public interface AccountService {

    /**
     * 从数据库中load用户,按照id逆序排列
     *
     * @param status 用户状态
     * @param pageNo 页码
     * @param count  每页返回条数
     * @return 分页结果
     */
    Paging<User> list(@ParamInfo("status")Integer status, @ParamInfo("pageNo")Integer pageNo, @ParamInfo("count")Integer count);

    /**
     * user related CRUD begins
     */
    User findUserById(@ParamInfo("id")Long id);

    User findUserByName(@ParamInfo("name")String name);

    Long createUser(User user);

    void updateUser(User user);

    /**
     * @param userId user id
     */
    void changePassword(Long userId, String oldPassword, String newPassword);

    /**
     * @param id              id
     * @param encryptPassword encryptPassword
     */
    void resetPassword(Long id, String encryptPassword);


    Map<String, String> refreshToken(String email, String token);

    /**
     * 删除用户,如果尝试删除超级管理员将抛出异常.
     */
    void deleteUser(Long id);

    @Nullable
    User findUserByEmail(@ParamInfo("email")String email);

    /**
     * 根据手机号码查询用户
     *
     * @param mobile 手机号码
     * @return 用户
     */
    User findUserByMobile(@ParamInfo("mobile")String mobile);


    User login(String email, String password);


    /**
     * ddbao支持手机号码登陆
     *
     * @param mobile   手机号码
     * @param password 密码
     * @return 用户
     */
    User loginByMobile(String mobile, String password);

    /**
     * 修改手机号码
     *
     * @param userId   用户Id
     * @param mobile   新的手机号码
     * @param password 用户密码
     */
    void changeMobile(Long userId, String mobile, String password);
}
