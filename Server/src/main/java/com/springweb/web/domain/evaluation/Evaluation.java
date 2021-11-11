package com.springweb.web.domain.evaluation;

import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Evaluation extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "EVALUATION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "TEACHER_ID")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "STUDENT_ID")
    private Student student;

    private String content; //평가내용

    private double starPoint; //별점 (1~5)

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
}