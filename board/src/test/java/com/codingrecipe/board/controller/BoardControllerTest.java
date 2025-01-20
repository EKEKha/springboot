package com.codingrecipe.board.controller;

import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@WebMvcTest(BoardRestController.class)
//@AutoConfigureWebMvc   //이 어노테이션을 통해 MockMvc를 Builder 없이 주입받을 수 있음
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //BoardController 잡고있는 Bean객체에 대해 Mock형태의 객체 생성
    @MockitoBean
    BoardService boardService;

    @Test
    @DisplayName("게시글 데이터 가져오기 테스트")
    void 게시글_가져오기()throws Exception{

        long id = 1;

        //given : MockMvc 객체가 특정 상황에서 해야하는 행위를 정의하는 메서드
        //값 id를 받는 메서드가 수행될때 BoardDTO 어던 값이 retrun 될거다.
        given(boardService.findById(id))
                .willReturn(
                new BoardDTO(id,"@2","asdf","asdf","boardCon",0,"2024.12.13",4,new ArrayList<>()));


        //andExcpect: 기대하는 값이 나왔는지 체크 해볼 수있는 메소드

        mockMvc.perform(get("/"+id))
                .andExpect(jsonPath("$.boardWriter").exists())
                .andDo(print());


    }


}