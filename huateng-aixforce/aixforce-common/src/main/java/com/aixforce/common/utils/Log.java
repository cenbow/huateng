package com.aixforce.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-9-19
 */
public class Log {

    public static Logger of(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static Logger of(Object that) {
        return of(that.getClass());
    }
}
