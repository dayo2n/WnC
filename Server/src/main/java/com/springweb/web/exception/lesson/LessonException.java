package com.springweb.web.exception.lesson;

import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.BaseExceptionType;

public class LessonException extends BaseException  {

    private BaseExceptionType exceptionType;

    public LessonException(BaseExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    @Override
    public BaseExceptionType getExceptionType() {
        return exceptionType;
    }

}
