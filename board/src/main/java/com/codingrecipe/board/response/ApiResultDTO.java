package com.codingrecipe.board.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
public class ApiResultDTO<T> {


     //private int code;
     private final HttpStatus status;
     //private String status;
     private String message;
     private T data;





}
