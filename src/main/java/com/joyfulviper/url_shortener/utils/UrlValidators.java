package com.joyfulviper.url_shortener.utils;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlValidators {
    private static final UrlValidator urlValidator = new UrlValidator();

    public static boolean isValid(String url) {
        return urlValidator.isValid(url);
    }
}
