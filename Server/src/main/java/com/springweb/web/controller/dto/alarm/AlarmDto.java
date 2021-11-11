package com.springweb.web.controller.dto.alarm;

import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.domain.alarm.AlarmType;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AlarmDto {

    private Long id;

    private AlarmType alarmType;//SEND_APPLY, APPROVED,REFUSED, COMPLETION

    private LocalDateTime createdDate; //알람이 온 날짜


    private LessonDto lesson;//알람의 대상이 되는 강의 정보

    private StudentDto applicantMember; //강의를 신청한 학생


    public AlarmDto(Alarm alarm){
        this.id = alarm.getId();
        this.alarmType = alarm.getAlarmType();
        this.createdDate =alarm.getCreatedDate();

        this.applicantMember = new StudentDto(alarm.getApplicantMember());
        this.lesson = new LessonDto(alarm.getLesson());
    }



    private static class LessonDto {
        private Long lessonId;
        private String title;
        private String teacherName; //XX님의 강의 title에 요청이 들어왔어요! 이런식

        public LessonDto(Lesson lesson) {
            this.lessonId = lesson.getId();
            this.title = lesson.getTitle();
            this.teacherName = lesson.getTeacher().getName();
        }
    }

    private static class StudentDto {
        private Long applicantStudentId;
        private String studentName; //XX학생이 가입 요청을 보냈어요! 이런식

        public StudentDto(Student student) {
            this.applicantStudentId = student.getId();
            this.studentName = student.getName();
        }
    }
}
