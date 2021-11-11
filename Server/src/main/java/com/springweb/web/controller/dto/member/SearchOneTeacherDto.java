package com.springweb.web.controller.dto.member;

import com.springweb.web.domain.evaluation.Evaluation;
import com.springweb.web.domain.member.Teacher;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class SearchOneTeacherDto {

    private Long teacherId;
    private String teacherName;//선생님의 이름
    private int age; //나이
    private String profileImgPath;//프사 URL, 풀 경로 저장
    private String career;//경력
    private double starPoint; //선생님이 받은 별점의 평균

    public SearchOneTeacherDto(Teacher teacher) {
        this.teacherId = teacher.getId();
        this.teacherName = teacher.getName();
        this.age = teacher.getAge();
        this.profileImgPath = teacher.getProfileImgPath();
        this.career = teacher.getCareer();
        this.starPoint = teacher.getStarPoint();
    }
}
