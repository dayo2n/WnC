package com.springweb.web.controller.dto.alarm;

import com.springweb.web.controller.dto.lesson.SearchLessonDto;
import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.domain.alarm.AlarmType;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.service.lesson.LessonType;
import lombok.Data;
import org.springframework.data.domain.Page;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchAlarmDto {

    /**
     * 총 페이지 수
     * 현재 페이지 번호
     * 현재 페이지에 존재하는 과외들의 개수
     * 총 과외 수
     * <p>
     * ==알람 정보==
     * id
     * alarmType => 알람타입
     * 알람 생성일
     * lesson;//알람의 대상이 되는 강의 정보
     * applicantMember; //강의를 신청한 학생 (없을수도 있음)
     * isRead; //읽었으면 true, 안읽었으면 false
     */
    private int totalPageNum;
    private int currentPageNum;
    private long totalElementCount;
    private int currentPageElementCount;



    private List<SimpleAlarmDto> simpleAlarmList = new ArrayList<>();




    private static class SimpleAlarmDto {
        private Long id;
        private AlarmType alarmType;//알람타입
        private boolean isRead; //읽었으면 true, 안읽었으면 false
        private LocalDateTime createdDate; //알람이 온 날짜

        private LessonDto lessonDto;//알람의 대상이 되는 강의 정보
        private StudentDto applicantMemberDto; //강의를 신청한 학생

        public SimpleAlarmDto(Alarm alarm) {
            this.id = alarm.getId();
            this.alarmType = alarm.getAlarmType();
            this.isRead = alarm.isRead();
            this.createdDate= alarm.getCreatedDate();

            this.lessonDto = new LessonDto(alarm.getLesson());
            this.applicantMemberDto = new StudentDto(alarm.getApplicantMember());
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




    public SearchAlarmDto(Page<Alarm> alarmPage) {
        this.totalPageNum = alarmPage.getTotalPages();
        this.currentPageNum = alarmPage.getNumber();
        this.currentPageElementCount = alarmPage.getNumberOfElements();
        this.totalElementCount = alarmPage.getTotalElements();


        this.simpleAlarmList = alarmPage.getContent().stream()
                .map(alarm -> new SimpleAlarmDto(alarm)).toList();
    }
}
