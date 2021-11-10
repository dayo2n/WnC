package com.springweb.web.controller.dto.member;

import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Teacher;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TeacherDto {

    @Column(unique = true)
    private String username;//카카오로 로그인 하는 경우에도 아이디는 입력
    private String name;//직접 입력
    private int age; //나이
    private String profileImgPath;//프사 URL
    private String career;//경력
    private List<LessonDto> lessonList = new ArrayList<>();//내가 올린 강의



    @Data
    private class LessonDto {
        private Long lessonId;
        private String title;
        private LocalDateTime createdDate;//강의를 생성한 날짜

        public LessonDto(Lesson lesson) {
            this.lessonId = lesson.getId();
            this.title = lesson.getTitle();
            this.createdDate = lesson.getCreatedDate();
        }
    }


    public TeacherDto(Teacher teacher) {
        this.username = teacher.getUsername();
        this.name = teacher.getName();
        this.age = teacher.getAge();
        this.profileImgPath = teacher.getProfileImgPath();
        this.career = teacher.getCareer();

        this.lessonList = teacher.getLessonList().stream().map(lesson -> new LessonDto(lesson)).toList();//배치사이즈로 해결

    }
}
