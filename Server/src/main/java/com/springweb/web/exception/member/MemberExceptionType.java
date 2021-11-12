package com.springweb.web.exception.member;

import com.springweb.web.exception.BaseExceptionType;
import org.springframework.http.HttpStatus;

public enum MemberExceptionType implements BaseExceptionType {

    //== 회원가입, 로그인 시 ==//
    ALREADY_EXIST_USERNAME(600, HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    USERNAME_NOT_EXIST(601, HttpStatus.NOT_FOUND, "아이디가 존재하지 않습니다."),
    PASSWORDS_DOES_NOT_MATCH(602, HttpStatus.NOT_FOUND, "비밀번호가 일치하지 않습니다."),
    MUST_REGISTER(603, HttpStatus.UNAUTHORIZED, "회원가입을 진행해야 합니다."),
    PLEASE_LOGIN_AGAIN(604, HttpStatus.UNAUTHORIZED, "다시 로그인 해주세요"),  //왜 이런 오류가 발생했는지 모를 때
    CANNOT_LEAVE_BSC_GROUP_LESSON_HAS_STUDENT(605, HttpStatus.FORBIDDEN, "그룹 레슨에 가입한 학생이 있어서 탈퇴하지 못합니다. 모집을 완료 후 다시 시도해주세요."),
    NOT_FOUND_MEMBER(606, HttpStatus.NOT_FOUND, "회원 정보가 없습니다.");



    private int errorCode;
    private HttpStatus httpStatus;
    private String errorMessage;

    MemberExceptionType(int errorCode, HttpStatus httpStatus, String errorMessage) {
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
