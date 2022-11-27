package com.joyfulviper.url_shortener.controller.exception;

import com.joyfulviper.url_shortener.dto.ExceptionDto;
import com.joyfulviper.url_shortener.exception.UrlShortenException;
//import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
//@Slf4j
public class ExceptionHandlerController {

    @ExceptionHandler(UrlShortenException.class)
    private ResponseEntity<ExceptionDto> handleUrlShortenException(UrlShortenException urlShortenException) {
        ExceptionDto response = ExceptionDto.getExceptionInformation(urlShortenException);
        errorLogging(urlShortenException);
        HttpStatus httpStatus = urlShortenException.getHttpStatus();
        return new ResponseEntity<>(response, httpStatus);
    }

    private void errorLogging(Exception exception) {
        //log.error("Exception: {} , message: {}", exception.getClass().getSimpleName(), exception.getMessage());
        System.out.println("Exception: " + exception.getClass().getSimpleName() + " message: " + exception.getMessage());
    }
}
