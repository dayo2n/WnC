package com.springweb.web.controller.dto.evaluation;

import com.springweb.web.domain.member.Teacher;
import lombok.Data;

/**
 * 선생님 평가시 사용
 */
@Data
public class EvaluationTeacherDto {

    private Long id;
    private String name;//직접 입력
    private int age; //나이
    private String profileImgPath;//프사 URL
    private String career;//경력
    private double starPoint;//별점

    public EvaluationTeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.age = teacher.getAge();
        this.profileImgPath = teacher.getProfileImgPath();
        this.career = teacher.getCareer();
        this.starPoint = teacher.getStarPoint();
    }

}


/*@Data
public class EvaluationTeacherDto {

    private Long id;
    private String name;//직접 입력
    private int age; //나이
    private String profileImgPath;//프사 URL
    private String career;//경력
    private double starPoint;//별점

    public EvaluationTeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.age = teacher.getAge();
        this.profileImgPath = teacher.getProfileImgPath();
        this.career = teacher.getCareer();
        this.starPoint = teacher.getStarPoint();
    }

}*/
