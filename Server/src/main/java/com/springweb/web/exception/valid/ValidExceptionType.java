package com.springweb.web.exception.valid;

import com.springweb.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum ValidExceptionType implements BaseExceptionType {


    FORMAT_ERROR(1000, HttpStatus.BAD_REQUEST, "형식 오류가 있습니다");


    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    ValidExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    ValidExceptionType(String errorMessage) {
        this.errorCode = 1000;
        this.httpStatus = HttpStatus.BAD_REQUEST;
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
