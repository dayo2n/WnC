package com.springweb.web.exception.alarm;

import com.springweb.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum AlarmExceptionType implements BaseExceptionType {

    NO_AUTHORITY_READ_ALARM(1101, HttpStatus.FORBIDDEN, "알람을 읽을 권한이 없습니다."),
    NO_EXIST_ALARM(1102, HttpStatus.FORBIDDEN, "알람이 존재하지 않습니다."),
    ALARM_PRODUCE_EXCEPTION(1111, HttpStatus.FORBIDDEN, "알람이 생성되는 과정에서 오류가 발생했습니다.");



    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    AlarmExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
