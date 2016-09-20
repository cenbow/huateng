/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.storage.impl;

import com.aixforce.storage.Storable;
import com.aixforce.storage.Storage;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Desc:
 * Author: dimzfw@gmail.com
 * Date: 8/30/12 3:04 PM
 */
@Component("inMemoryStore")
public class InMemoryStore implements Storage {
    private static Map<String, Storable> data = Maps.newConcurrentMap();

    @Override
    public Storable get(String key) {
        return data.get(key);
    }


    @Override
    public boolean put(Storable storable) {

        data.put(storable.key(), storable);
        return true;
    }

    @Override
    public void remove(String key) {
       data.remove(key);
    }


}
