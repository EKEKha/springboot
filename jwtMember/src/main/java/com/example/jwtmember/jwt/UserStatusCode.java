package com.example.jwtmember.jwt;

//추후 convert 사용
public enum UserStatusCode{

    ACTIVE("USE001", "정상"),
    SLEEP("USE002", "휴먼"),
    LOCK("USE003", "잠금"),
    WITHDRAW("USE099", "탈퇴");


    private final String code;
    private final String description;

    UserStatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
