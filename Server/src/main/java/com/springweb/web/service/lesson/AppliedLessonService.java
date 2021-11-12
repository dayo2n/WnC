package com.springweb.web.service.lesson;

import com.springweb.web.exception.BaseException;

public interface AppliedLessonService {

    /**
     * 과외 신청 => 선생님께 알람 (여기서 채팅 기능을 도입해서 서로 대회하게 만든 후, 과외신청이 완료되면 과외인원 추가)을 보냄, 이미 모집완료된 강의면 오류 발생
     *
     * 과외 신청 수락 => 과외 신청시 교수님께 알람 -> 교수님이 OK 버튼을 누르면, 과외 신청이 수락됨
     *              => 이때 개인과외면 모집완료 => 그룹과외면 현재 학생수 1 추가
     *              => + TakingLesson 에 추가해야 함.
     *               =>거부하면 아쉽..^^
     *
     *
     * 과외 신청 취소 => (전제조건 : 선생님과 이미 돈과 수업 관련된 상담이 끝나서 서로 취소하기로 협의한 상태)
     *              => 모집완료된 과외일 경우 불가능, 개인과외일 경우 불가능(개인과외면 이미 신청이 수락된 순간 모집완료이므로)
     *              => 그룹과외일 경우 (똑같이 취소알람이 교수님께 감, OK 누르면 듣는 학생수 -1, TakingLesson 삭제
     *
     * 모집 완료 => 인원 모두 충족되면 자동 모집완료, 인원이 부족하더라도 중간에 모집완료 가능
     *
    */

    //== 과외 신청 ==//
    void apply(Long lessonId) throws BaseException;

    //== 과외 신청 취소==//
    void cancel(Long lessonId) throws BaseException;

    //== 과외 신청 수락==//
    void accept(Long lessonId, Long alarmId) throws BaseException;

    //== 과외 신청 거절==//
    void refuse(Long lessonId, Long alarmId) throws BaseException;




    //== 모집완료 ==//
    void applyCompleted(Long lessonId) throws BaseException;



}
