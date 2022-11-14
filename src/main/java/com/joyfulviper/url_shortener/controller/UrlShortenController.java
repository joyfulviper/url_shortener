package com.joyfulviper.url_shortener.controller;

import com.joyfulviper.url_shortener.domain.Url;
import com.joyfulviper.url_shortener.dto.ResponseUrl;
import com.joyfulviper.url_shortener.dto.UrlDto;
import com.joyfulviper.url_shortener.exception.UrlValidException;
import com.joyfulviper.url_shortener.service.UrlService;
import com.joyfulviper.url_shortener.utils.UrlValidators;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UrlShortenController {
    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<?> createShortUrl(@Valid @RequestBody UrlDto url) throws URISyntaxException {

        if (UrlValidators.isValid(url.url())) {
            Url requestUrl = urlService.save(url.url());
            URI uri = new URI("/" + requestUrl.getShortenUrl());
            String shortenUrl = "http://localhost:8080/" + requestUrl.getShortenUrl();
            ResponseUrl responseUrl = new ResponseUrl(shortenUrl, urlService.getRequestCount(requestUrl.getShortenUrl()));
            return ResponseEntity.created(uri).body(responseUrl);
        }
        throw new UrlValidException();
    }

    @GetMapping("{shortUrl}")
    public ResponseEntity<Url> searchByShortenUrl(@PathVariable String shortUrl) throws URISyntaxException {
        Url url = urlService.findByShortenUrl(shortUrl);
        URI redirectUri = new URI(url.getOriginalUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        return ResponseEntity
                .status(HttpStatus.SEE_OTHER)
                .headers(httpHeaders)
                .build();
    }

    @PostMapping("search")
    public ResponseEntity<ResponseUrl> getRequestCountByShortenUrl(@Valid @RequestBody UrlDto shortUrl) {
        final int shortenSize = 8;
        String key = shortUrl.url().substring(shortUrl.url().length() - shortenSize);
        ResponseUrl responseUrl = new ResponseUrl(shortUrl.url(), urlService.getRequestCount(key));
        return new ResponseEntity<>(responseUrl, HttpStatus.OK);
    }

}