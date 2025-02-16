package com.example.jwtmember.response;


import lombok.Builder;
import lombok.Getter;


@Getter
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResultDTO<T> {


     private String  status;
     private String message;
     private T data;


     @Builder
     public ApiResultDTO(ResultCode status, String message, T data) {
          this.status = status.getStatus();
          this.message = message;
          this.data = data;
     }

}
