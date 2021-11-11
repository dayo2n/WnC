package com.springweb.web.exception.lesson;

import com.springweb.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum LessonExceptionType implements BaseExceptionType {


    NO_AUTHORITY_CREATE_LESSON(700, HttpStatus.FORBIDDEN, "과외를 등록할 권한이 없습니다."),
    NO_AUTHORITY_DELETE_LESSON(701, HttpStatus.FORBIDDEN, "과외를 삭제할 권한이 없습니다."),
    NO_AUTHORITY_UPDATE_LESSON(702, HttpStatus.FORBIDDEN, "과외를 수정할 권한이 없습니다."),
    NO_AUTHORITY_APPLY_LESSON(704, HttpStatus.FORBIDDEN, "과외를 신청할 권한이 없습니다."),
    CAN_NOT_DELETE_LESSON_EXIST_STUDENT(705, HttpStatus.FORBIDDEN, "모집한 학생이 존재하여 과외를 삭제하실 수 없습니다."),
    CAN_NOT_DELETE_LESSON_COMPLETED(705, HttpStatus.FORBIDDEN, "모집이 완료된 과외는 삭제하실 수 없습니다"),
    NOT_FOUND_LESSON(710, HttpStatus.NOT_FOUND, "없는 과외입니다");




    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    LessonExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

}
