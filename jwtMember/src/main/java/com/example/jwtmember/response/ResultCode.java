package com.example.jwtmember.response;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS("success"),
    FAIL("fail"),
    ERROR("error");

    private final String status;


}
