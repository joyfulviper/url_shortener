package com.joyfulviper.url_shortener.exception;


public class UrlShortenException extends RuntimeException{
    private final ExceptionStatus responseStatus;

    public UrlShortenException(ExceptionStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }
}
