package com.codingrecipe.board.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
/*
* statusCode return 방법
* return ResponseEntity 혹은
* @ResponseStatus 사용
*
* */

/*해당예외들은 서비스 단에서 처리 못한 exception 런타임 exception을 다룰 예정*/


@Slf4j
@RestControllerAdvice
public class ApiGlobalExceptionHandler {

    //



    //NullPointerException
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> handleNullPointerException(NullPointerException  e) {

        log.info("NullPointerException 발생: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }

    //ArrayIndexOutOfBoundsException
    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<String> handleArrayIndexOutOfBoundsException(ArrayIndexOutOfBoundsException  e) {

        log.info("ArrayIndexOutOfBoundsException 발생: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }



    //Exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception  e) {

        log.info("Exception 발생: {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }



}
