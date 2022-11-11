package com.joyfulviper.url_shortener.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

public class Sha256Test {

    @DisplayName("8글자 인코딩 확인 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"search?q=hello&oq=hello&aqs=chrome.0.0i355i433i512j46i433i512j0i433i512j0i131i433i512j0i512j46i512j46i131i433i512j69i61.1283j0j4&sourceid=chrome&ie=UTF-8}",
            "asdf"})
    public void 인코딩_확인_테스트(String input) {
        assertThat(Sha256.encode(input, 0)).hasSize(8);
    }
}