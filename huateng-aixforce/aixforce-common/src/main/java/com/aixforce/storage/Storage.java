/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.storage;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/30/12 11:49 AM
 */
public interface Storage {

    Storable get(String key);

    boolean put(Storable storable);

    void remove(String key);

}

