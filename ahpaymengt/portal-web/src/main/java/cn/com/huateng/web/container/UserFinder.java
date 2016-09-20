/*
 * Copyright (c) 2013 杭州端点网络科技有限公司
 */

package cn.com.huateng.web.container;

/**
 * Desc: used to find userId in different conditions.
 * Author: dimzfw@gmail.com
 * Date: 7/12/13 9:39 PM
 */
public interface UserFinder {
    Long getUserId(Long someId);
}
