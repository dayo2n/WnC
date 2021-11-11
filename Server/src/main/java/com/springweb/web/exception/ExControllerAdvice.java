package com.springweb.web.exception;

import com.springweb.web.exception.lesson.LessonException;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.valid.ValidException;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.net.BindException;
import java.util.Locale;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class ExControllerAdvice {

    private final MessageSource messageSource;

    @ExceptionHandler
    public ResponseEntity ExHandler(MemberException e, Locale locale){

        log.error("오류 발생{}" ,e.getExceptionType().getErrorMessage());

        return makeResponseEntity(e,locale);
    }

    @ExceptionHandler
    public ResponseEntity ExHandler(LessonException e, Locale locale){

        log.error("포스트 관련 오류 발생{}" ,e.getExceptionType().getErrorMessage());

        return makeResponseEntity(e,locale);
    }

    @ExceptionHandler
    public ResponseEntity ExHandler(ValidException e, Locale locale){

        log.error("바인딩 관련 오류 발생{}" ,e.getMessage());

        return makeResponseEntity(e,locale);
    }




    private ResponseEntity makeResponseEntity(BaseException e, Locale locale){

        String message = messageSource.getMessage(e.getExceptionType().toString(),null, locale);

        int httpStatusCode = e.getExceptionType().getHttpStatus().value();
        int errorCode =e.getExceptionType().getErrorCode();

        ErrorDto errorDto = ErrorDto.builder()
                .errorCode(errorCode)
                .httpStatusCode(httpStatusCode)
                .errorMessage(message)
                .build();

        return new ResponseEntity(errorDto,e.getExceptionType().getHttpStatus());
    }

    @AllArgsConstructor
    @Data
    @NoArgsConstructor
    @Builder
    private static class ErrorDto{
        private int errorCode;
        private int httpStatusCode;
        private String errorMessage;

    }

}
