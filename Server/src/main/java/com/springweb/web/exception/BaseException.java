package com.springweb.web.exception;

public abstract class BaseException extends Exception{

    public abstract BaseExceptionType getExceptionType();
}
