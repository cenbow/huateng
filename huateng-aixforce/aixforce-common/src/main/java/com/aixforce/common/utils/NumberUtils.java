package com.aixforce.common.utils;

import java.text.DecimalFormat;

/**
 * Created by IntelliJ IDEA.
 * User: AnsonChan
 * Date: 13-6-4
 */
public class NumberUtils {
    private static final DecimalFormat df = new DecimalFormat("0.##");

    public static String formatPrice(Number price) {
        if(price==null){
            return "";
        }
        return df.format(price.doubleValue() / 100);
    }
}
