package com.codingrecipe.board.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;
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

    // NoHandlerFoundException (404 처리: 잘못된 URL)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.warn("NoHandlerFoundException: {}", ex.getMessage());

//
//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.NOT_FOUND,
//                "잘못된 URL 요청입니다.",
//                "입력한 URL 경로를 확인해주세요."
//        );

          ErrorResponse errorResponse = ErrorResponse.builder()
                  .status(HttpStatus.NOT_FOUND)
                  .message("잘못된 URL 요청입니다")
                  .errors(List.of("입력한 URL 경로를 확인해 주세요"))
                  .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.info("BusinessException 발생: "+errorCode.getDetailMsg());
        ErrorResponse response = ErrorResponse.toErrorResponse(errorCode);
        return ResponseEntity.status(errorCode.getHttpStatus()).body(response);
    }


    @ExceptionHandler(Exception.class)  // 모든 예외를 처리
    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
        // 모든 예외에 대한 기본적인 예외 처리
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.valueOf("처리되지 않은 예외"), e.getMessage(), List.of(e.toString()));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}
