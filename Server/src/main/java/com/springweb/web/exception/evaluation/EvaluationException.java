package com.springweb.web.exception.evaluation;

import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.BaseExceptionType;

public class EvaluationException extends BaseException  {

    private BaseExceptionType exceptionType;

    public EvaluationException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }

}
