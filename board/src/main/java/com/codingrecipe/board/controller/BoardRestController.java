package com.codingrecipe.board.controller;


import com.codingrecipe.board.dto.BoardDTO;
import com.codingrecipe.board.dto.BoardFileDTO;
import com.codingrecipe.board.exception.BusinessException;
import com.codingrecipe.board.response.ApiResultDTO;
import com.codingrecipe.board.response.ResultCode;
import com.codingrecipe.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor // final이 붙은 필드만 가지고 생성자를 만들어줌 , 생성자 주입..
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String save(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException { //@ModelAttribute 생략 가능
        System.out.println("boardDTO= " + boardDTO);
        boardService.save(boardDTO);
        return "redirect:/list";
    }

    @GetMapping("/list")
    public ResponseEntity<?> findAll(Model model){

        List <BoardDTO> boardDTOList = boardService.findAll();
        //model.addAttribute("boardList",boardDTOList);

        ApiResultDTO apiResultDTO= ApiResultDTO
                .builder()
                .data(boardDTOList)
                .status(ResultCode.SUCCESS)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResultDTO);

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id, Model model) {
        // 조회수 처리
        boardService.updateHits(id);
        // 상세내용 가져옴
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        System.out.println("boardDTO = " + boardDTO);

        if(boardDTO ==null){
            ApiResultDTO apiResultDTO= ApiResultDTO
                    .builder()
                    .data(boardDTO)
                    .status(ResultCode.FAIL)
                    .message("게시글 정보가 존재하지 않습니다")
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResultDTO);

        }


        if (boardDTO.getFileAttached() == 1) {
            List<BoardFileDTO> boardFileDTOList = boardService.findFile(id);
            model.addAttribute("boardFileList", boardFileDTOList);
        }

        ApiResultDTO apiResultDTO= ApiResultDTO
                .builder()
                .data(boardDTO)
                .status(ResultCode.SUCCESS)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(apiResultDTO);

    }
    @GetMapping("/update/{id}")
    public String update(@PathVariable("id")Long id , Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board",boardDTO);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String update(BoardDTO boardDTO , Model model){
         boardService.update(boardDTO);
        BoardDTO dto = boardService.findById(boardDTO.getId());
        model.addAttribute("board",dto);
        return "detail";
    }
}

/*
* @RequestParam 파라미터 바인딩
* @ModelAttribute 객체 바인딩
* */