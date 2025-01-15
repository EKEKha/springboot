package com.codingrecipe.board.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;


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
