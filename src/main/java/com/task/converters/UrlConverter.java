package com.task.converters;

import com.datastax.driver.core.utils.UUIDs;
import com.task.domain.ShortToUrl;
import com.task.domain.UrlToShort;
import com.task.dtos.ShortifyDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UrlConverter {

    @Value("${short-url.prefix}")
    private String prefix;

    public ShortifyDto convert(String longUrl, Long shortUrl) {
        ShortifyDto shortifyDto = new ShortifyDto();
        shortifyDto.setLongUrl(longUrl);
        shortifyDto.setShortUrl(prefix + shortUrl);
        return shortifyDto;
    }

    public ShortifyDto convert(ShortToUrl shortToUrl) {
        return convert(shortToUrl.getUrl(), shortToUrl.getShortUrl());
    }

    public ShortifyDto convert(UrlToShort urlToShort) {
        return convert(urlToShort.getUrl(), urlToShort.getShortUrl());

    }

}
