package com.joyfulviper.url_shortener.exception;

//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

//@RequiredArgsConstructor
//@Getter
public enum ExceptionStatus {

    NOT_FOUND_SHORT_URL(404, "해당 URL이 존재하지 않습니다.", NOT_FOUND),
    URL_VALID_EXCEPTION(400, "URL형식이 올바르지 않습니다.", BAD_REQUEST);

    private final int status;
    private final String message;
    private final HttpStatus httpStatus;

    ExceptionStatus(int status, String message, HttpStatus httpStatus) {
        this.status = status;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
