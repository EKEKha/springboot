package com.codingrecipe.board.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDTO {
    private Long id;

    private String boardWriter;

    private String boardPass;

    @NotBlank(message = "제목을 적어주세요.") //null 불가, "" 불가, " "불가
    @Size(min = 3, max = 100, message = "제목은 3자 이상 100자 이하입니다.")
    private String boardTitle;

    private String boardContents;

    private int boardHits;

    private String createdAt;

    private int fileAttached;

    private List<MultipartFile> boardFile;




}
