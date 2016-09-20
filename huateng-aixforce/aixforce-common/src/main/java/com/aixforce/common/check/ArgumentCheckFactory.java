/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.common.check;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 11/22/12 1:04 PM
 */
@Service
public class ArgumentCheckFactory {

    private static Map<String, Checker> checkerMap = Maps.newHashMap();

    public Checker getChecker(String objectName) {
        return checkerMap.get(objectName);
    }

    public void addChecker(String objectName, Checker checker) {
        checkerMap.put(objectName, checker);
    }
}
