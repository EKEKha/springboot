package com.codingrecipe.board.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;


@Getter //해당 필드값들을 읽어서 HttpResponse 객체 만들때 필수
//@Builder 모든 매개변수로 생성자 생성
public class ErrorResponse {

    private  HttpStatus status;
    private  String message;
    private   List<String> errors;




    //@Builder(builderMethodName = "errorsBuilder")// builderMethodName  생략시  builder()로 접근, 오버라이딩된 메소드들에 각각명칭 부여하기위해 생성자위에 사용
    public ErrorResponse(HttpStatus status, String message, List<String> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }


    //@Builder(builderMethodName = "errorBuilder") //메서드이름 부여했으나 작동안함..errors가 null로 작동
    public ErrorResponse(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = List.of(error);
    }

    public ErrorResponse( String message, String error) {
        this.message = message;
        this.errors = List.of(error);
    }


    public ErrorResponse(String message){
        this.message=message;


    }

    //에러코드 기준으로 손쉽게 객체 생성(설정된 메세지 사용할 경우)
    public static ErrorResponse toErrorResponse(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getHttpStatus(),
                errorCode.name(),
                errorCode.getDetailMsg());
    }

    public static ErrorResponse toErrorResponse(String msg,String error) {
        return new ErrorResponse(
               msg,error);
    }

    public static ErrorResponse toErrorResponse(String msg) {
        return new ErrorResponse(
                msg);
    }




}
