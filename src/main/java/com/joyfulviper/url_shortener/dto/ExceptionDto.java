package com.joyfulviper.url_shortener.dto;

import com.joyfulviper.url_shortener.exception.UrlShortenException;

public record ExceptionDto(String status, String message) {
    public static ExceptionDto getExceptionInformation(UrlShortenException exception) {
        return new ExceptionDto(exception.getHttpStatus().toString(), exception.getMessage());
    }
}
