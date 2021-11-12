package com.springweb.web.exception.alarm;

import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.BaseExceptionType;

public class AlarmException extends BaseException  {

    private BaseExceptionType exceptionType;

    public AlarmException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }

}
