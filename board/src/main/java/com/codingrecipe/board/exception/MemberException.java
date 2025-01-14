package com.codingrecipe.board.exception;

public class MemberException extends BusinessException{
    public MemberException() {
        super(ErrorCode.MISMATCH_USER);
    }

}
