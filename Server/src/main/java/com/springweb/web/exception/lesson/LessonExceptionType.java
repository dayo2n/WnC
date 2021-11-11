package com.springweb.web.exception.lesson;

import com.springweb.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum LessonExceptionType implements BaseExceptionType {


    NO_AUTHORITY_CREATE_LESSON(700, HttpStatus.FORBIDDEN, "과외를 등록할 권한이 없습니다."),
    NO_AUTHORITY_DELETE_LESSON(701, HttpStatus.FORBIDDEN, "과외를 삭제할 권한이 없습니다."),
    NO_AUTHORITY_UPDATE_LESSON(702, HttpStatus.FORBIDDEN, "과외를 수정할 권한이 없습니다."),
    NO_AUTHORITY_APPLY_LESSON(704, HttpStatus.FORBIDDEN, "과외를 신청할 권한이 없습니다."),

    CAN_NOT_DELETE_LESSON_EXIST_STUDENT(705, HttpStatus.FORBIDDEN, "모집한 학생이 존재하여 과외를 삭제하실 수 없습니다."),
    CAN_NOT_APPLY_LESSON_COMPLETED(706, HttpStatus.FORBIDDEN, "모집이 완료된 과외는 신청하실 수 없습니다"),
    CAN_NOT_DELETE_LESSON_COMPLETED(707, HttpStatus.FORBIDDEN, "모집이 완료된 과외는 삭제하실 수 없습니다"),
    CAN_NOT_CHANGE_LESSON_TYPE(708, HttpStatus.FORBIDDEN, "과외의 타입을 바꿀 수 없습니다."),
    ALREADY_COMPLETED(708, HttpStatus.FORBIDDEN, "이미 모집완료된 강의입니다."),
    ALREADY_APPLIED(709, HttpStatus.FORBIDDEN, "이미 신청하신 강의입니다."),
    NOT_FOUND_LESSON(710, HttpStatus.NOT_FOUND, "없는 과외입니다"),
    CAN_NOT_APPLY_CANCEL(711, HttpStatus.NOT_FOUND, "모집완료된 강의는 취소하실 수 없습니다."),
    CAN_NOT_ACCEPT_STUDENT_COMPLETED(712, HttpStatus.BAD_REQUEST,"이미 모집이 완료되었으므로 학생을 더 받을 수 없습니다." ),
    CAN_NOT_ACCEPT_STUDENT_CANCEL(712, HttpStatus.BAD_REQUEST,"학생이 강의 수강을 취소하였습니다." ),
    NO_AUTHORITY_ACCEPT_LESSON(713, HttpStatus.FORBIDDEN,"강의 요청을 수락하실 권한이 없습니다" ),
    ETC_EXCEPTION(799,HttpStatus.BAD_REQUEST, "불가능한 요청이 들어옴");


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
