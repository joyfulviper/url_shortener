package com.joyfulviper.url_shortener.exception;

public class UrlValidException extends UrlShortenException {
    public UrlValidException() {
        super(ExceptionStatus.URL_VALID_EXCEPTION);
    }
}
