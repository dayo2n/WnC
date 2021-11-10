package com.springweb.web.exception.member;

import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.BaseExceptionType;

public class MemberException extends Exception implements BaseException {

    private BaseExceptionType exceptionType;

    public MemberException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }

}
