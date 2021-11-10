package com.springweb.web.controller.dto.member;

import com.springweb.web.domain.lesson.TakingLesson;
import com.springweb.web.domain.member.Role;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class StudentDto {

    @Column(unique = true)
    private String username;//카카오로 로그인 하는 경우에도 아이디는 입력
    private String name;//직접 입력
    private int age; //나이
    private String profileImgPath;//프사 URL
    private List<TakingLessonDto> takingLectureList = new ArrayList<>();//듣는 강의 목록

    @Data
    private class TakingLessonDto {
        private Long lessonId;//레슨 id
        private String title;//레슨 제목
        private LocalDateTime createdDate;//내가 수강한 날짜
        private String teacherUsername;//선생님 username
        private String teacherName;//선생님 이름

        public TakingLessonDto(TakingLesson takingLesson) {
            this.lessonId = takingLesson.getId();
            this.title = takingLesson.getTitle();
            this.createdDate = takingLesson.getCreatedDate();
            this.teacherUsername = takingLesson.getTeacherUsername();
            this.teacherName = takingLesson.getTeacherName();
        }


    }


    public StudentDto(Student student) {
        this.username = student.getUsername();
        this.name = student.getName();
        this.age = student.getAge();
        this.profileImgPath = student.getProfileImgPath();


        this.takingLectureList = student.getTakingLectureList()
                .stream().map(takingLesson -> new TakingLessonDto(takingLesson)).toList();//-> 배치사이즈로 해결
    }
}
