package com.joyfulviper.url_shortener.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@Getter
public enum ExceptionStatus {

    NOT_FOUND_SHORT_URL(404, "해당 URL이 존재하지 않습니다", NOT_FOUND),
    ORIGIN_URL_NOT_MATCH_PROTOCOL(400, "http:// or https:// 로 시작해야합니다", BAD_REQUEST);

    private final int status;
    private final String message;
    private final HttpStatus httpStatus;

}