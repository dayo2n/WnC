package com.springweb.web.domain.lesson;

import com.springweb.web.controller.dto.member.StudentDto;
import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.member.Student;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TakingLesson extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "TAKING_LESSON_ID")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;


    private String title;//레슨 제목
    private String teacherName;//레슨하시는 선생님 이름
    private String teacherUsername;//레슨하시는 선생님 username


}
