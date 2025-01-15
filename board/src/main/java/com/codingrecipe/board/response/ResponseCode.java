package com.codingrecipe.board.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(HttpStatus.OK),
    FAILURE(HttpStatus.BAD_REQUEST);

    /*성공,실패 추후에는 더 세분화?*/

    //private int code;

    private final HttpStatus httpStatus;



}
