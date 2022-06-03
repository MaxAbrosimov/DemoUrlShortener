package com.task.utils;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class UrlShortener {

    private UrlShortener() {}

    public static String shortifyUrl(String url) {
        return Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
    }


}
