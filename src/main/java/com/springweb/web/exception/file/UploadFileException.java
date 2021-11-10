package com.springweb.web.exception.file;

import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.BaseExceptionType;


public class UploadFileException extends RuntimeException implements BaseException {
    private BaseExceptionType exceptionType;


    public UploadFileException(BaseExceptionType bt) { //생성자
        super(bt.getErrorMessage());
        exceptionType = bt;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
