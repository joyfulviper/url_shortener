package com.joyfulviper.url_shortener.exception;


import org.springframework.http.HttpStatus;

public class UrlShortenException extends RuntimeException {
    private final ExceptionStatus responseStatus;

    public UrlShortenException(ExceptionStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public HttpStatus getHttpStatus() {
        return responseStatus.getHttpStatus();
    }
}
