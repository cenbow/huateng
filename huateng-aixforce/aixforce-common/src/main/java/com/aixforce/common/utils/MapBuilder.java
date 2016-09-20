package com.aixforce.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-9-16
 */
public class MapBuilder<K, V> {
    private Map<K, V> map;
    private boolean ignoreNullValue = false;

    private MapBuilder() {
        this.map = new HashMap<K, V>();
    }

    private MapBuilder(Map<K, V> map) {
        this.map = map;
    }

    public static <K, V> MapBuilder<K, V> of() {
        return new MapBuilder<K, V>();
    }

    public static <K, V> MapBuilder<K, V> of(Map<K, V> map) {
        return new MapBuilder<K, V>(map);
    }

    public static <K, V> MapBuilder<K, V> newHashMap() {
        return of(new HashMap<K, V>());
    }

    public static <K, V> MapBuilder<K, V> newTreeMap() {
        return of(new TreeMap<K, V>());
    }

    public MapBuilder<K, V> ignoreNullValue() {
        ignoreNullValue = true;
        return this;
    }

    public MapBuilder<K, V> put(K key, V value) {
        if (ignoreNullValue && value == null) {
            return this;
        }
        map.put(key, value);
        return this;
    }

    public Map<K, V> map() {
        return map;
    }
}
