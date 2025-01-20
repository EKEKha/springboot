package com.codingrecipe.board.exception;

public class MemberException extends BusinessException{
    public MemberException() {
        super(ErrorCode.FORBIDDEN);
    }

    public MemberException(String message){
        super(message);

    }

}
