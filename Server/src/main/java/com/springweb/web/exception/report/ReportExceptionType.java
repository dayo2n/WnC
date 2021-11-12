package com.springweb.web.exception.report;

import com.springweb.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum ReportExceptionType implements BaseExceptionType {


    NO_AUTHORITY_REPORT_TEACHER(1200, HttpStatus.FORBIDDEN, "선생님을 신고할 권한이 없습니다");





    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    ReportExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
