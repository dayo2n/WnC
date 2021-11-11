package com.springweb.web.exception.file;

import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.BaseExceptionType;


public class UploadFileException extends BaseException  {
    private BaseExceptionType exceptionType;


    public UploadFileException(BaseExceptionType bt) { //생성자
        exceptionType = bt;
    }

    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }
}
