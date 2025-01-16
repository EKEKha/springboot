package com.codingrecipe.board.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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
    public ResponseEntity<?> handleNullPointerException(NullPointerException  e) {

        log.info("NullPointerException 발생: {}", e.getMessage(), e);

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "잘못된 URL 요청입니다.",
                "입력한 URL 경로를 확인해주세요."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
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


        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND,
                "잘못된 URL 요청입니다.",
                "입력한 URL 경로를 확인해주세요."
        );

//          ErrorResponse errorResponse = ErrorResponse.errorBuilder()
//                  .status(HttpStatus.NOT_FOUND)
//                  .message("잘못된 URL 요청입니다")
//                  .error("dd")
//                  .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleRuntimeException(BusinessException e) {

        log.info("BusinessException 발생: "+e.toString());
        ErrorResponse response = ErrorResponse.toErrorResponse(e.getErrorMsg(),e.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    //pring MVC에서 메서드 파라미터의 타입이 일치X

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "잘못된 파라미터 형식입니다. '"
                + ex.getName() + "' 파라미터의 값이 '"
                + ex.getValue() + "'으로 잘못되었습니다.";

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.valueOf(400), errorMessage, ex.toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)  // 모든 예외를 처리
    public ResponseEntity<?> handleAllExceptions(Exception e) {
        // 모든 예외에 대한 기본적인 예외 처리

        log.info("NonCatch exception 발생: "+e.toString());

//        ErrorResponse errorResponse = new ErrorResponse(
//                HttpStatus.valueOf(400), e.getMessage(), e.toString());
//        //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);



        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponse.toErrorResponse("내부서버 에러 관리자에게 문의 해주세요"));

//        ErrorResponse errorResponse = ErrorResponse.errorBuilder()//errors null로 나옴 사용 X 직접 객체생성하자
//                .status(HttpStatus.valueOf(400))
//                .message("내부 서버 에러입니다.")
//                .error("왜안나와?")
//                .build();
//        log.info("Exception 발생: {}", e.getMessage(), e);

        //return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }

}
