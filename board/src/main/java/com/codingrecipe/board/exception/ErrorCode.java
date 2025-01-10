package com.codingrecipe.board.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

/*추후 successCode도 추가 예정*/


@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    MISMATCH_USER(HttpStatus.BAD_REQUEST, "아이디나 비밀번호를 확인해 주세요."),
    TOKEN_TYPE(HttpStatus.BAD_REQUEST, "토큰 타입이 올바르지 않습니다."),
    UNAVAILABLE_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "사용할 수 없는 토큰 입니다."),

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
    FORBIDDEN(HttpStatus.UNAUTHORIZED, "접근 권한이 없습니다."),
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "세션이 만료되었습니다. 다시 로그인해주세요."),




    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 이메일을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "리프레쉬 토큰을 찾을 수 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이메일이 이미 존재합니다."),
//    DELETED_EMAIL(HttpStatusCONFLICT, "이미 삭제된 이메일 입니다."),
    DUPLICATE(HttpStatus.CONFLICT, "ID가 이미 존재합니다.");


    private final HttpStatus httpStatus;
    private final String detailMsg;


}
