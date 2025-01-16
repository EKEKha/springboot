package com.codingrecipe.board.exception;

public class BusinessException extends RuntimeException  {
//    public static final RuntimeException INVALID_NICKNAME = new RuntimeException(ErrorResponse.INVALID_NICKNAME);
//    public static final RuntimeException INVALID_PARAMETER = new RuntimeException(ErrorResponse.INVALID_PARAMETER);
//    public static final RuntimeException INVALID_TOKEN = new RuntimeException(ErrorResponse.INVALID_TOKEN);

    /*
    모든 exception은 Throwable 상속되어있기 때문에 fillInStackTrace() 해당 메서드를 가지고있다.
    * 기본생성자에서 fillInStackTrace()호출
    * */

    /*스택 트레이스 정보가 불필요하기 때문에 fillInStackTrace 메서드 재정의*/
    @Override
    public synchronized Throwable fillInStackTrace(){
        return this;
    }

    private  ErrorCode errorCode;
    private String errorMsg ;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getDetailMsg());
        this.errorCode = errorCode;
    }

    public BusinessException(String errorMsg){
        super(errorMsg);
        this.errorMsg=errorMsg;
    }


    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

}
