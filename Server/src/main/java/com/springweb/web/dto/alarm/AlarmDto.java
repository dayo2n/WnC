package com.springweb.web.dto.alarm;

import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.domain.alarm.AlarmType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmDto {

    private Long id;
    private AlarmType alarmType;//SEND_APPLY, APPROVED,REFUSED, COMPLETION
    private LocalDateTime createdDate; //알람이 온 날짜
    private Long lessonId;
    private String title;
    private String teacherName; //XX님의 강의 title에 요청이 들어왔어요! 이런식
    private Long applicantStudentId;
    private String applicantStudentName; //XX학생이 가입 요청을 보냈어요! 이런식



    public AlarmDto(Alarm alarm){
        this.id = alarm.getId();
        this.alarmType = alarm.getAlarmType();
        this.createdDate =alarm.getCreatedDate();
        this.lessonId=alarm.getLessonId();
        this.title=alarm.getLessonTitle();
        this.teacherName=alarm.getLessonTeacherName();
        this.applicantStudentId=alarm.getApplicantMemberId();
        this.applicantStudentName=alarm.getApplicantMemberName();

    }




}
