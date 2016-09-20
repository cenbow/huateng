/*
 * Copyright (c) 2012 杭州端点网络科技有限公司
 */

package com.aixforce.site.service;


import java.util.Map;

/**
 * Desc:  处理css
 * Author: dimzfw@gmail.com
 * Date: 12/8/12 4:51 PM
 */
public class CssProcessor {

    public static String process(Map<String, Object> style) {

        StringBuilder styleStr = new StringBuilder();

        for (String key : style.keySet()) {
            String value = "" + style.get(key);
            if (key.equals("background-color")) {
                //gen rgb rgba(1,1,1,1)  ==> rgb(1,1,1); if last is 0 :transparent
                String[] rgba = value.split(",");
                if (rgba != null && rgba.length == 4) {
                    String transparent = rgba[3].replace(" ", "");
                    if (transparent.equals("0.0)")||transparent.equals("0)")) {
                        styleStr.append("background-color:transparent;");
                    } else {
                        styleStr.append("background-color:").
                                append(rgba[0].replace("rgba", "rgb")).append(",").
                                append(rgba[1]).append(",").
                                append(rgba[2]).append(");");
                    }
                }
            }
            styleStr.append(key).append(":").append(value).append(";");
        }


        return styleStr.toString();
    }
}
