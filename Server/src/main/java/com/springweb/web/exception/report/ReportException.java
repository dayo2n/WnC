package com.springweb.web.exception.report;

import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.BaseExceptionType;

public class ReportException extends BaseException {

    private BaseExceptionType exceptionType;

    public ReportException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }

}
