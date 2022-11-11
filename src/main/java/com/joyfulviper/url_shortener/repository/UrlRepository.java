package com.joyfulviper.url_shortener.repository;

import com.joyfulviper.url_shortener.domain.Url;

import java.util.Optional;

public interface UrlRepository {
    Url save(String originalUrl);
    Optional<Url> findByShortenUrl(String shortenUrl);
    Integer getRequestCount(Url url);
}
