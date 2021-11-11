package com.springweb.web.controller.dto.lesson;

import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.service.lesson.LessonType;
import lombok.Data;
import org.springframework.data.domain.Page;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SearchLessonDto {

    /**
     * 총 페이지 수
     * 현재 페이지 번호
     * 현재 페이지에 존재하는 과외들의 개수
     * 총 과외 수
     * <p>
     * ==과외 정보==
     * id
     * 제목
     * 교수님 정보
     * 개인과외인지,그룹과외인지
     */
    private int totalPageNum;
    private int currentPageNum;
    private long totalElementCount;
    private int currentPageElementCount;


    private List<SimpleLessonDto> simpleLectureDtoList = new ArrayList<>();


    private class SimpleLessonDto {
        private Long id;

        private String title;//제목

        private int views;//조회수

        private String teacherName;//작성자(선생님 이름) => 페치조인 사용,

        private int recommendCount;//좋아요 개수

        private LessonType lessonType ; // PERSONAL이면 개인과외, GROUP이면 그룹과외

        private int maxStudentCount;

        private int nowStudentCount;

        private boolean isCompleted; //모집완료 여부, true면 모집완료, false면 모집중

        private LocalDateTime period;//모집기간


        public SimpleLessonDto(Lesson lesson) {
            this.id = lesson.getId();
            this.title = lesson.getTitle();
            this.views = lesson.getViews();
            this.teacherName = lesson.getTeacher().getName();
            this.recommendCount = lesson.getRecommendCount();

            this.maxStudentCount = lesson.getMaxStudentCount();
            this.isCompleted = lesson.isCompleted();

            this.lessonType = LessonType.PERSONAL;

            if(lesson instanceof GroupLesson groupLesson){
                this.lessonType = LessonType.GROUP;
                this.nowStudentCount = groupLesson.getNowStudentCount();
                this.period = groupLesson.getPeriod();
            }
        }


    }


    public SearchLessonDto(Page<Lesson> lessonPage) {
        this.totalPageNum = lessonPage.getTotalPages();
        this.currentPageNum = lessonPage.getNumber();

        this.currentPageElementCount = lessonPage.getNumberOfElements();
        this.totalElementCount = lessonPage.getTotalElements();

        this.simpleLectureDtoList = lessonPage.getContent().stream()
                .map(lesson -> new SimpleLessonDto(lesson)).toList();
    }
}
