package com.aixforce.web.controller;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.util.UUID;

/**
 * User: 董培基
 * Date: 13-11-22
 * Time: 上午10:36
 */
public class EncryptPassword {

    private final static HashFunction md5 = Hashing.md5();

    private final static HashFunction sha512 = Hashing.sha512();

    private final static Joiner joiner = Joiner.on('@').skipNulls();

    private final static Splitter splitter = Splitter.on('@').trimResults();

    public static void main(String[] args) {
        String salt = md5.newHasher().putUnencodedChars(UUID.randomUUID().toString()).putLong(System.currentTimeMillis()).hash()
                .toString().substring(0, 4);
        String realPassword = sha512.hashUnencodedChars("best_pay11888" + salt).toString().substring(0, 20);
        System.out.println(joiner.join(salt, realPassword));



        Iterable<String> parts = splitter.split("065b@336e600b8a13b720dc8b");
        String salt1 = Iterables.get(parts, 0);
        String realPassword1 = Iterables.get(parts, 1);
        System.out.println(Objects.equal(sha512.hashUnencodedChars("best_pay11888" + salt1).toString().substring(0, 20), realPassword1));
    }


}
