package com.joyfulviper.url_shortener.repository;

import com.joyfulviper.url_shortener.domain.Url;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class UrlRepository {
    private static Map<String, Url> shortUrlDump = new ConcurrentHashMap<>();

    public Url save(String shortenUrl, Url url) {
        shortUrlDump.put(shortenUrl, url);
        return url;
    }

    public Url findByShortenUrl(String shortUrl) {
        return shortUrlDump.get(shortUrl);
    }

    public Integer getRequestCount(String shortUrl) {
        return shortUrlDump.get(shortUrl).getRequestCount();
    }

    public boolean isExistByShortUrl(String url) {
        return shortUrlDump.containsKey(url);
    }
}
