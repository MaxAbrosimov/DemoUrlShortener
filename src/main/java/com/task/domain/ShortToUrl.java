package com.task.domain;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import org.thymeleaf.util.DateUtils;

import java.io.Serializable;

@Table("short_to_url")
public class ShortToUrl implements Serializable {

    @PrimaryKey
    private Long shortUrl;
    private String url;


    public ShortToUrl(String url) {
        this.shortUrl = DateUtils.createNow().getTimeInMillis();
        this.url = url;
    }

    public Long getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(Long shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
