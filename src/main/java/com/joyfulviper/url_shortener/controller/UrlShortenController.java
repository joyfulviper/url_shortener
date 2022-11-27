package com.joyfulviper.url_shortener.controller;

import com.joyfulviper.url_shortener.domain.Url;
import com.joyfulviper.url_shortener.dto.UrlDto;
import com.joyfulviper.url_shortener.dto.UrlResponseDto;
import com.joyfulviper.url_shortener.exception.UrlValidException;
import com.joyfulviper.url_shortener.service.UrlService;
import com.joyfulviper.url_shortener.utils.UrlValidators;
//import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
//@RequiredArgsConstructor
public class UrlShortenController {
    private final UrlService urlService;
    private final int SHORTEN_SIZE = 8;

    public UrlShortenController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<?> createShortUrl(@Valid @RequestBody UrlDto url, @Value("${domain}") String domain) throws URISyntaxException {
        if (UrlValidators.isValid(url.url())) {
            String requestUrl = urlService.save(url.url());
            URI uri = new URI("/" + requestUrl);
            String shortenUrl = domain + requestUrl;
            UrlResponseDto responseUrl = new UrlResponseDto(shortenUrl, urlService.getRequestCount(requestUrl));
            return ResponseEntity.created(uri).body(responseUrl);
        }
        throw new UrlValidException();
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Url> searchByShortenUrl(@PathVariable String shortUrl) throws URISyntaxException {
        Url url = urlService.findByShortenUrl(shortUrl);
        URI redirectUri = new URI(url.getOriginalUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return ResponseEntity
                .status(HttpStatus.MOVED_PERMANENTLY)
                .headers(httpHeaders)
                .build();
    }

    @PostMapping("/search")
    public ResponseEntity<UrlResponseDto> getRequestCountByShortenUrl(@Valid @RequestBody UrlDto shortUrl) {
        String key = shortUrl.url().substring(shortUrl.url().length() - SHORTEN_SIZE);
        UrlResponseDto responseUrl = new UrlResponseDto(shortUrl.url(), urlService.getRequestCount(key));
        return new ResponseEntity<>(responseUrl, HttpStatus.OK);
    }

}