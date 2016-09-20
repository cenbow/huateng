package com.aixforce.site.model.redis;

import com.google.common.base.Objects;

/*
* Author: jlchen
* Date: 2012-11-29
*/
public enum Color {
    BLUE(0,"绿色"),
    YELLOW(1,"黄色"),
    RED(2,"红色"),
    PURPLE(3,"紫色"),
    PINK(4,"粉色"),
    BROWN(5,"褐色"),
    GREEN(6,"绿色"),
    GRAY(7,"灰色"),
    OTHER(8,"其他");

    private final int value;

    private final String display;

    private Color(int value, String display) {
        this.value = value;
        this.display = display;
    }

    public static Color fromNumber(int value){
        for (Color color : Color.values()) {
            if(Objects.equal(color.value,value)){
                return color;
            }
        }
        return null;
    }

    public int toNumber(){
        return value;
    }

    @Override
    public String toString() {
        return display;
    }
}
