package com.codingrecipe.board.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS("success"),
    FAIL("fail");

    private final String status;


}
