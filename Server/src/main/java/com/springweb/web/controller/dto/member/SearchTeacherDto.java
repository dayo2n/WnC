package com.springweb.web.controller.dto.member;

import com.springweb.web.controller.dto.lesson.SearchLessonDto;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.service.lesson.LessonType;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SearchTeacherDto {
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


    private List<TeacherProfileDto> teacherProfiles = new ArrayList<>();



    private class TeacherProfileDto {
        private Long id;
        private String name;//직접 입력
        private int age; //나이
        private String profileImgPath;//프사 URL
        private String career;//경력
        private double starPoint;//별점

        public TeacherProfileDto(Teacher teacher) {
            this.id = teacher.getId();
            this.name = teacher.getName();
            this.age = teacher.getAge();
            this.profileImgPath = teacher.getProfileImgPath();
            this.career = teacher.getCareer();
            this.starPoint = teacher.getStarPoint();
        }
    }

    public SearchTeacherDto(Page<Teacher> teacherPage) {
        this.totalPageNum = teacherPage.getTotalPages();
        this.currentPageNum = teacherPage.getNumber();

        this.currentPageElementCount = teacherPage.getNumberOfElements();
        this.totalElementCount = teacherPage.getTotalElements();

        this.teacherProfiles = teacherPage.getContent().stream()
                .map(teacher -> new TeacherProfileDto(teacher)).toList();
    }
}
