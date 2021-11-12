package com.springweb.web.domain.alarm;

import javax.persistence.Embeddable;

public enum AlarmType {

    /**
     * SEND_APPLY => 과외 신청 보냈을 때  =>
     *              교수님 : XX학생이 YY과외에 가입신청을 보냈습니다. 수락하시겠습니까? (예, 아니오)
     *
     * APPROVED => 승인
     *              학생 : YY과외에 가입이 거절되었습니다.
     *
     * REFUSED   =>   거정
     *                학생 : YY과외에 가입이 거절되었습니다.
     *
     * COMPLETION => 교수님 + 학생들 모두에게 : YY 과외 모집이 완료되었습니다.
     *
     *
     *
     * SEND_APPLY, APPROVED, COMPLETION
     *
     * SEND_APPLY 이면 student, teacher, lesson이 모두 있어야 한다.
     * APPROVED   이면 student, lesson이 있어야 한다.
     * COMPLETION 이면 Student, lesson 또는 Professor Lesson이 있어야 한다.
     */


    SEND_APPLY, APPROVED,REFUSED, COMPLETION
}
