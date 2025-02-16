package com.example.jwtmember.exception;


import com.example.jwtmember.response.ApiResultDTO;
import com.example.jwtmember.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
/*
* statusCode return 방법
* return ResponseEntity 혹은
* @ResponseStatus 사용
*
* */

/*해당예외들은 서비스 단에서 처리 못한 exception 런타임 exception을 다룰 예정
* exception 내부
* */


@Slf4j
@RestControllerAdvice
public class ApiGlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ApiResultDTO> handleRuntimeException(BusinessException e) {

        log.info("BusinessException 발생: "+e.toString());
        ApiResultDTO response = ApiResultDTO.builder()
                .message(e.getErrorMsg())
                .status(ResultCode.ERROR)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }


}
