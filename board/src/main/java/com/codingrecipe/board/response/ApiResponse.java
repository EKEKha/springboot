package com.codingrecipe.board.response;


import com.codingrecipe.board.exception.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
public class ApiResponse<T> {


     private static final String SUCCESS = "success";
     private static final String ERROR = "error";


     private int code;

     private String status;
     private String message;
     private T data;


}
