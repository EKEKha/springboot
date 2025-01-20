package com.codingrecipe.board.config;

import com.codingrecipe.board.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;


@WebMvcTest
class WebConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    BoardService boardService;

    @Test
    public void testCORSHeaders() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.options("/list")
                        .header("Origin", "http://localhost:3000")  // 요청 Origin
                        .header("Access-Control-Request-Method", "GET"))  // 예상하는 요청 메서드
                .andExpect(header().string("Access-Control-Allow-Origin", "http://localhost:3000"))  // 허용된 Origin 확인
                .andExpect(header().string("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE"));  // 허용된 HTTP 메서드 확인
    }
    @Test
    void configureMessageConverters() {
    }
}