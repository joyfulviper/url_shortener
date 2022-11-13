package com.joyfulviper.url_shortener.controller;

import com.joyfulviper.url_shortener.domain.Url;
import com.joyfulviper.url_shortener.dto.ResponseUrl;
import com.joyfulviper.url_shortener.dto.UrlDto;
import com.joyfulviper.url_shortener.exception.UrlValidException;
import com.joyfulviper.url_shortener.service.UrlService;
import com.joyfulviper.url_shortener.utils.UrlValidators;
import lombok.RequiredArgsConstructor;
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
            ResponseUrl responseUrl = new ResponseUrl("http://localhost:8080/" + requestUrl.getShortenUrl());
            //System.out.println(responseUrl.shortUrl());
            return ResponseEntity.created(uri).body(responseUrl);
        }
            throw new UrlValidException();
    }


}
