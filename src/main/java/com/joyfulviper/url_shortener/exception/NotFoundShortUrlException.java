package com.joyfulviper.url_shortener.exception;

public class NotFoundShortUrlException extends UrlShortenException {
    public NotFoundShortUrlException() {
        super(ExceptionStatus.NOT_FOUND_SHORT_URL);
    }
}
