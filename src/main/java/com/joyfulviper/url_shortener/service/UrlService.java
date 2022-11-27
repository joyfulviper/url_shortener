package com.joyfulviper.url_shortener.service;

import com.joyfulviper.url_shortener.domain.Url;
import com.joyfulviper.url_shortener.exception.NotFoundShortUrlException;
import com.joyfulviper.url_shortener.repository.UrlRepository;
import com.joyfulviper.url_shortener.utils.Sha256;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


//@Slf4j
@Service
public class UrlService {

    private final UrlRepository urlRepository;
    private final int REQUEST_COUNT = 0;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String save(String originalUrl) {
        String shortUrl = createShortUrl(originalUrl);
        Url saveUrl = createUrl(shortUrl, originalUrl, REQUEST_COUNT);
        urlRepository.save(shortUrl, saveUrl);
        return saveUrl.getShortenUrl();
    }

    public Url findByShortenUrl(String shortUrl) {
        checkEmptyUrl(shortUrl);
        increaseRequestCount(shortUrl);
        return urlRepository.findByShortenUrl(shortUrl);
    }

    private void checkEmptyUrl(String shortUrl) {
        if (ObjectUtils.isEmpty(urlRepository.findByShortenUrl(shortUrl))) {
            throw new NotFoundShortUrlException();
        }
    }

    public Integer getRequestCount(String shortUrl) {
        checkEmptyUrl(shortUrl);
        return urlRepository.getRequestCount(shortUrl);
    }

    private void increaseRequestCount(String shortUrl) {
        Url url = urlRepository.findByShortenUrl(shortUrl);
        Url saveUrl = createUrl(shortUrl, url.getOriginalUrl(), url.getRequestCount() + 1);
        urlRepository.save(shortUrl, saveUrl);
    }

    private String createShortUrl(String originalUrl) {
        int initialPosition = 0;
        String shortUrl = Sha256.encode(originalUrl, initialPosition);
        while (urlRepository.isExistByShortUrl(shortUrl)) {
            shortUrl = Sha256.encode(originalUrl, ++initialPosition);
        }
        return shortUrl;
    }

    private Url createUrl(String shortUrl, String originalUrl, int requestCount) {
        Url saveUrl = Url.builder()
                .shortenUrl(shortUrl)
                .originalUrl(originalUrl)
                .requestCount(requestCount)
                .build();
        return saveUrl;
    }
}
