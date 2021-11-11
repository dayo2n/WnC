package com.springweb.web.exception.evaluation;

import com.springweb.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum EvaluationExceptionType implements BaseExceptionType {

    ALREADY_EVALUATE(900, HttpStatus.FORBIDDEN, "이미 평가한 선생님입니다."),
    NO_AUTHORITY_EVALUATE(901, HttpStatus.FORBIDDEN, "선생님을 평가할 권한이 없습니다");



    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    EvaluationExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

}
