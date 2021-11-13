package com.springweb.web.dto.alarm;

import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.domain.alarm.AlarmType;
import lombok.Data;
import org.springframework.data.domain.Page;

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




    @Data
    private static class SimpleAlarmDto {
        private Long id;
        private AlarmType alarmType;//알람타입
        private boolean isRead; //읽었으면 true, 안읽었으면 false
        private LocalDateTime createdDate; //알람이 온 날짜

        private Long lessonId;
        private String title;
        private String teacherName;


        private Long applicantStudentId;
        private String applicantStudentName; ; //강의를 신청한 학생

        public SimpleAlarmDto(Alarm alarm) {
            this.id = alarm.getId();
            this.alarmType = alarm.getAlarmType();
            this.isRead = alarm.isRead();
            this.createdDate= alarm.getCreatedDate();

            this.lessonId=alarm.getLessonId();
            this.title=alarm.getLessonTitle();
            this.teacherName=alarm.getLessonTeacherName();

            this.applicantStudentId=alarm.getApplicantMemberId();
            this.applicantStudentName=alarm.getApplicantMemberName(); ; //강의를 신청한 학생
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
