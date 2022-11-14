package com.joyfulviper.url_shortener.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfulviper.url_shortener.dto.UrlDto;
import com.joyfulviper.url_shortener.exception.UrlValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UrlShortenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("단축url 생성 컨트롤러 테스트")
    public void 단축url_생성_테스트() throws Exception {
        String originalUrl = "https://www.google.com/search?q=hello&oq=hel&aqs=chrome.0.0i355i433i512j46i433i512j69i57j0i433i512l4j69i61.811j0j7&sourceid=chrome&ie=UTF-8";
        UrlDto urlDto = new UrlDto(originalUrl);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urlDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("shortUrl").value("http://localhost:8080/US9nbHly"));
    }

    @Test
    @DisplayName("단축url 검증 오류 테스트")
    public void 단축_url_검증_오류_테스트() throws Exception {
        String originalUrl = " ";
        UrlDto urlDto = new UrlDto(originalUrl);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> mockMvc.perform(post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(urlDto)))
                )
        .hasCause(new UrlValidException());
    }

    @Test
    @DisplayName("단축 URL 조회 테스트")
    public void URL_조회_테스트() throws Exception {
        String originalUrl = "https://www.google.com/search?q=hello&oq=hel&aqs=chrome.0.0i355i433i512j46i433i512j69i57j0i433i512l4j69i61.811j0j7&sourceid=chrome&ie=UTF-8";
        UrlDto urlDto = new UrlDto(originalUrl);

        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urlDto)));
                //.andDo(print());

        String shortUrl = "http://localhost:8080/US9nbHly";
        mockMvc.perform(get(shortUrl))
                .andExpect(status().is(303));
    }

    @Test
    @DisplayName("단축 URL을 통한 요청횟수 조회 테스트")
    public void 요청횟수_조회_테스트() throws Exception {
        String originalUrl = "https://www.google.com/search?q=hello&oq=hel&aqs=chrome.0.0i355i433i512j46i433i512j69i57j0i433i512l4j69i61.811j0j7&sourceid=chrome&ie=UTF-8";
        UrlDto urlDto = new UrlDto(originalUrl);

        mockMvc.perform(post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(urlDto)));

        String shortUrl = "http://localhost:8080/US9nbHly";
        mockMvc.perform(get(shortUrl))
                .andExpect(status().is(303));

        mockMvc.perform(post("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new UrlDto(shortUrl))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("requestCount").value(1));

    }

}