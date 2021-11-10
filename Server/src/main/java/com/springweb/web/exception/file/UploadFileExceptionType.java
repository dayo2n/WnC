package com.springweb.web.exception.file;

import com.cnutime.cnupost.exception.BaseExceptionType;
import com.springweb.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum UploadFileExceptionType implements BaseExceptionType {

    FILE_NOT_FOUND(1300, HttpStatus.NOT_FOUND, "FILE_NOT_FOUND."),
    FILE_COULD_NOT_BE_SAVED(1301, HttpStatus.INTERNAL_SERVER_ERROR, "FILE_COULD_NOT_BE_SAVED"),
    PATH_COULD_NOT_BE_DELETED(1302, HttpStatus.INTERNAL_SERVER_ERROR, "FILE_COULD_NOT_BE_DELETED");

    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    UploadFileExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
