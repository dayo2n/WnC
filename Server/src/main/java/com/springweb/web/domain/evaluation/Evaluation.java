package com.springweb.web.domain.evaluation;

import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import lombok.Getter;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "EVALUATION")
public class Evaluation extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EVALUATION_ID")
    private Long id;


    private String content; //평가내용

    private double starPoint; //별점 (1~5)

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;





    public Evaluation(String content, double starPoint) {
        this.content = content;


        this.starPoint = starPoint;
    }

    public void confirmTeacher(Teacher teacher) {
        teacher.addEvaluation(this);
        this.teacher = teacher;
    }

    public void confirmStudent(Student student) {

        student.addEvaluation(this);

        this.student = student;
    }

    //== 학생 회원탈퇴 시 사용 ==//
    public void deleteStudent(){
        this.student =null;
    }


}