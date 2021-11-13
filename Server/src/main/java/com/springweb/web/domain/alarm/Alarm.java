package com.springweb.web.domain.alarm;


import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import lombok.*;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Alarm extends BaseTimeEntity {
    /**
     * 과외 관련 =>
     * 과외 신청을 보냈습니다 => 과외 신청이 도착했습니다(수락하시겠습니까? 네, 아니오)
     * X과외에 가입되었습니다. X과외에서 가입을 거부했습니다.
     *
     * 과외 모집이 완료되었습니다
     */
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ALARM_ID")
    private Long id;

    /**
     * SEND_APPLY => 과외 신청 보냈을 때  =>
     *              교수님 : XX학생이 YY과외에 가입신청을 보냈습니다. 수락하시겠습니까? (예, 아니오)
     *
     * APPROVED => 승인 여부(가입되었습니다, 거절되었습니다)
     *              학생 : YY과외에 가입되었습니다. YY과외에 가입이 거절되었습니다.
     *
     * REFUSED =>   거정
     *                학생 : YY과외에 가입이 거절되었습니다.
     *
     * COMPLETION => 교수님 + 학생들 모두에게 : YY 과외 모집이 완료되었습니다.
     *
     *
     *
     * SEND_APPLY, APPROVED, COMPLETION
     *
     * SEND_APPLY 이면 student, teacher, lesson이 모두 있어야 한다.
     * APPROVED , REFUSE   이면 student, lesson이 있어야 한다.
     * COMPLETION 이면 Student, lesson 또는 Professor Lesson이 있어야 한다.
     */
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member target; //알람을 받는 사람


    private String applicantMemberName;
    private Long applicantMemberId;

    private String lessonTitle;
    private Long lessonId;
    private String lessonTeacherName;




    private boolean isRead; //읽었으면 true, 안읽었으면 false
    //쪽지 온 시간은 baseTimeEntity를 상속받아 해결


    public void read(){
        this.isRead = true;
    }

}
