package com.aixforce.redis.utils;

/**
 * Author:  <a href="mailto:jlchen.cn@gmail.com">jlchen</a>
 * Date: 2013-08-02
 */
public class JedisUtils {
    private static final String OK_CODE = "OK";
    private static final String OK_MULTI_CODE = "+OK";

    /**
     * Check status is OK or +OK.
     */
    public static boolean isStatusOk(String status) {
        return (status != null) && (OK_CODE.equals(status) || OK_MULTI_CODE.equals(status));
    }
}
