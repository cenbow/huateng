/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.container;


import com.google.common.collect.ImmutableSet;

import java.util.Set;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 12/1/12 10:42 AM
 */
public class TempRoleSupportPage {
    private static Set<String> roleSupportPages = ImmutableSet.of("i");

    public static boolean isRoleSupport(String path) {
        return roleSupportPages.contains(path);
    }
}
