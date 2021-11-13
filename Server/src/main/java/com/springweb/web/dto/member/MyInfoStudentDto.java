package com.springweb.web.dto.member;


import com.springweb.web.domain.evaluation.Evaluation;
import com.springweb.web.domain.lesson.AppliedLesson;
import com.springweb.web.domain.lesson.TakingLesson;
import com.springweb.web.domain.member.Student;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 내정보 보기에서 사용!
 */
@Data
public class MyInfoStudentDto {



    private Long id; //username대신 id
    private String name;//이름
    private int age; //나이
    private String profileImgPath;//프사 URL
    private boolean isKakaoMember;

    private List<TakingLessonDto> takingLessonList = new ArrayList<>();//듣는 강의 목록
    private List<AppliedLessonDto> appliedLessonList = new ArrayList<>();//신청한 강의 목록
    private List<EvaluationDto> evaluationList = new ArrayList<>();//작성한 평가

    @Data
    private class TakingLessonDto {
        private Long lessonId;//레슨 id
        private String title;//레슨 제목
        private LocalDateTime createdDate;//내가 수강한 날짜
        private Long teacherId;//선생님 id
        private String teacherName;//선생님 이름

        public TakingLessonDto(TakingLesson takingLesson) {
            this.lessonId = takingLesson.getId();
            this.title = takingLesson.getTitle();
            this.createdDate = takingLesson.getCreatedDate();
            this.teacherId = takingLesson.getTeacherId();
            this.teacherName = takingLesson.getTeacherName();
        }


    }


    public MyInfoStudentDto(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.age = student.getAge();
        this.profileImgPath = student.getProfileImgPath();
        this.isKakaoMember = student.isKakaoMember();

        this.takingLessonList = student.getTakingLessonList()
                .stream().map(takingLesson -> new TakingLessonDto(takingLesson)).toList();//-> 배치사이즈로 해결

        this.appliedLessonList = student.getAppliedLessonList()
                .stream().map(appliedLesson -> new AppliedLessonDto(appliedLesson)).toList();//
        this.evaluationList = student.getEvaluationList()
                .stream().map(evaluation -> new EvaluationDto(evaluation)).toList();//
    }



    @Data
    private static class AppliedLessonDto {
        private Long lessonId;
        private String title;//신청한 과외의 게시물의 이름
        private LocalDateTime createdDate;//내가 신청한 날짜
        private Long teacherId;
        private String teacherName;

        public AppliedLessonDto(AppliedLesson appliedLesson) {
            lessonId = appliedLesson.getLesson().getId();
            title=appliedLesson.getTitle();
            createdDate=appliedLesson.getCreatedDate();
            teacherId=appliedLesson.getTeacherId();
            teacherName=appliedLesson.getTeacherName();
        }
    }


    @Data
    private static class EvaluationDto {
        private Long evaluationId;
        private String content;
        private double starPoint;
        private Long teacherId;
        private String teacherName;

        public EvaluationDto(Evaluation evaluation) {
            evaluationId = evaluation.getId();
            content = evaluation.getContent();
            starPoint = evaluation.getStarPoint();
            teacherId = evaluation.getTeacher().getId();
            teacherName = evaluation.getTeacher().getName();
        }
    }
}
