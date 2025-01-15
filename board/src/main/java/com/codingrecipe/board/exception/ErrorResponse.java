package com.codingrecipe.board.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;



//@Builder 모든 매개변수로 생성자 생성
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final List<String> errors;


    @Builder(builderMethodName = "errorsBuilder")// builderMethodName  생략시  builder()로 접근, 오버라이딩된 메소드들에 각각명칭 부여하기위해 생성자위에 사용
    public ErrorResponse(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }


    @Builder(builderMethodName = "errorBuilder")
    public ErrorResponse(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = List.of(error);
    }

    //에러코드 기준으로 손쉽게 객체 생성(설정된 메세지 사용할 경우)
    public static ErrorResponse toErrorResponse(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus(),
                errorCode.name(),
                errorCode.getDetailMsg());
    }
}
