package com.joyfulviper.url_shortener.service;

import com.joyfulviper.url_shortener.domain.Url;
import com.joyfulviper.url_shortener.exception.NotFoundShortUrlException;
import com.joyfulviper.url_shortener.utils.Sha256;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class UrlService {

    private static final Map<String, Url> shortUrlDump = new ConcurrentHashMap<>();

    public Url save(String originalUrl) {
        int position = 0;
        int requestCount = 0;
        String shortUrl = Sha256.encode(originalUrl, position);
        while (isExistByShortUrl(shortUrl)) {
            shortUrl = Sha256.encode(originalUrl, ++position);
        }
        Url saveUrl = Url.builder()
                .shortenUrl(shortUrl)
                .originalUrl(originalUrl)
                .requestCount(requestCount)
                .build();
        shortUrlDump.put(saveUrl.getShortenUrl(), saveUrl);
        return saveUrl;
    }

    public Url findByShortenUrl(String shortUrl) {
        if (ObjectUtils.isEmpty(shortUrlDump.get(shortUrl))) {
            //log.info("shortUrl -> {}", shortUrl);
            throw new NotFoundShortUrlException();
        }
        increaseRequestCount(shortUrl);

        return shortUrlDump.get(shortUrl);
    }

    public Integer getRequestCount(String shortUrl) {
        return shortUrlDump.get(shortUrl).getRequestCount();
    }

    private boolean isExistByShortUrl(String url) {
        return shortUrlDump.containsKey(url);
    }

    private void increaseRequestCount(String shortUrl) {
        Url url = shortUrlDump.get(shortUrl);

        Url saveUrl = Url.builder()
                .shortenUrl(url.getShortenUrl())
                .originalUrl(url.getOriginalUrl())
                .requestCount(url.getRequestCount() + 1)
                .build();

        shortUrlDump.put(saveUrl.getShortenUrl(), saveUrl);
    }
}
