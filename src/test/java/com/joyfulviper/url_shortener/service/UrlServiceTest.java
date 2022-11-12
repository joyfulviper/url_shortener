package com.joyfulviper.url_shortener.service;

import com.joyfulviper.url_shortener.domain.Url;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlServiceTest {

    UrlService urlService = new UrlService();

    @DisplayName("Url 변환 후 저장 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"asdf", "1adsf4d1", "gasdfgbacvz123sa1&%#"})
    public void Url_변환_저장_테스트(String input) {
        Url inputValue = urlService.save(input);
        Url result = urlService.findByShortenUrl(inputValue.getShortenUrl());
        assertThat(inputValue.getOriginalUrl()).isEqualTo(result.getOriginalUrl());
    }

    @DisplayName("원래 URL로 다시 단축 URL 생성해도 항상 새로운 단축 URL 생성 테스트 ")
    @Test
    public void 원본Url_재단축시_새로운Url_생성_테스트() {
        String[] input = {"asdf", "asdf", "asdf"};
        HashSet<String> set = new HashSet<>();
        for (int i = 0; i < input.length; i++) {
            set.add(urlService.save(input[i]).getShortenUrl());
        }
        assertThat(set.size()).isEqualTo(3);
    }

    @DisplayName("URL조회시 카운트 증가 확인 테스트")
    @Test
    public void url조회시_카운트_증가_테스트() {

        String shortUrl = urlService.save("asdfadsf").getShortenUrl();
        urlService.findByShortenUrl(shortUrl);
        urlService.findByShortenUrl(shortUrl);
        urlService.findByShortenUrl(shortUrl);
        assertThat(urlService.getRequestCount(shortUrl)).isEqualTo(3);
    }

}