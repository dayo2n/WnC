package com.springweb.web.exception.valid;

import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.BaseExceptionType;

public class ValidException extends BaseException {

    private BaseExceptionType exceptionType;

    public ValidException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }

}