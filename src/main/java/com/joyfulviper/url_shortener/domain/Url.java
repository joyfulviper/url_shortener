package com.joyfulviper.url_shortener.domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class Url {
    private String originalUrl;
    private String shortenUrl;
    private int requestCount;
}
