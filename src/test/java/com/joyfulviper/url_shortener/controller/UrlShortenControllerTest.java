package com.joyfulviper.url_shortener.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfulviper.url_shortener.dto.UrlDto;
import com.joyfulviper.url_shortener.exception.UrlValidException;
import com.joyfulviper.url_shortener.service.UrlService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.AccessDeniedException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class UrlShortenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Logger logger = LoggerFactory.getLogger(this.getClass());


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
    @DisplayName("단축url 생성 컨트롤러 테스트")
    public void blank_url() throws Exception {
        String originalUrl = " ";
        UrlDto urlDto = new UrlDto(originalUrl);

        org.assertj.core.api.Assertions.assertThatThrownBy(
                () -> mockMvc.perform(post("/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(urlDto)))
                )
        .hasCause(new UrlValidException());
    }

}