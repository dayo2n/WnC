package com.springweb.web.domain.lesson;

import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.member.Student;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
    private Long teacherId;//레슨하시는 선생님 id



}
