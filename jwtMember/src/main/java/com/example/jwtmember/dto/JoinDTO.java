package com.example.jwtmember.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class JoinDTO {

    private String loginId;
    private String password;


}
