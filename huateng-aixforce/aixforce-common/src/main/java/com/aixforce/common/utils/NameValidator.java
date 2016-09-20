package com.aixforce.common.utils;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;

import java.util.Set;
import java.util.regex.Pattern;

/*
 * Author: jlchen
 * Date: 2012-12-24
 */
public class NameValidator {
    private final static Pattern pattern = Pattern.compile("([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5]|_)+");

    private final static Pattern digits = Pattern.compile("[0-9]+");

    private final static Splitter DOT_SEP = Splitter.on('.').omitEmptyStrings().trimResults();

    private final static Splitter PATH_SEP = Splitter.on('/').omitEmptyStrings().trimResults();

    private final static Set<String>  reservedUserNames = ImmutableSet.<String>builder().add("admin")
            .add("管理员").add("系统管理员").add("ddbao").add("多多堡").add("system").add("administrator").add("root").build();

    private final static Set<String> reservedSubDomain = ImmutableSet.<String>builder().add("admin")
            .add("design").add("item").add("search").add("static").add("assets").add("news").add("blog").add("support")
            .add("mail").add("forum").add("email").add("group").build();

    private final static Set<String> reservedPath = ImmutableSet.<String>builder().add("design/").add("api/").add("images/")
            .add("oauth/").add("admin/").add("oauth2").build();

    public static boolean validate(String name){
        return Strings.isNullOrEmpty(name) || pattern.matcher(name).matches(); //对于空字符串,也认为是合法的
    }

    public static boolean isAllowedUserName(String name){
        return !reservedUserNames.contains(name.toLowerCase());
    }

    public static boolean isAllowedSubDomain(String subDomain){
        if(Strings.isNullOrEmpty(subDomain)){
            return true;
        }
        String prefix = Iterables.get(DOT_SEP.split(subDomain), 0);
        if(digits.matcher(prefix).matches()){
            return true;
        }
        if(prefix.length()<4||prefix.length()>20){
            return false;
        }
        if(!pattern.matcher(prefix).matches()){
            return false;
        }
        return !reservedSubDomain.contains(prefix.toLowerCase());
    }

    public static boolean isAllowedPath(String path){
        if(Strings.isNullOrEmpty(path)){
            return true;
        }
        String prefix = Iterables.get(PATH_SEP.split(path), 0)+'/';
        return !reservedPath.contains(prefix.toLowerCase());
    }

}
