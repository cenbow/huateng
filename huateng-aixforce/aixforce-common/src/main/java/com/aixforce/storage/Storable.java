/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.storage;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/30/12 11:51 AM
 */
public class Storable implements java.io.Serializable {

    private static final long serialVersionUID = 10101L;

    private String key;
    protected final Object target;
    private final Long expiredSeconds;

    public Storable(String key, Object target, Long expiredSeconds) {
        this.key = key;
        this.target = target;
        if (expiredSeconds > 0) {
            this.expiredSeconds = System.currentTimeMillis() + expiredSeconds * 1000;
        } else {
            this.expiredSeconds = -1L;
        }
    }

    public boolean isExpired() {
        return this.expiredSeconds != -1 && System.currentTimeMillis() > this.expiredSeconds;
    }

    public String key() {
        return key;
    }
}