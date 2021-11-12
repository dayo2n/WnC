package com.springweb.web.controller.dto.member;

import com.springweb.web.domain.evaluation.Evaluation;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.Lesson;
import com.springweb.web.domain.lesson.PersonalLesson;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.myconst.EvaluationConstName;
import com.springweb.web.service.lesson.LessonType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 내정보 보기에서 사용!
 */
@Data
public class MyInfoTeacherDto {



    private Long id;
    private String name;//직접 입력
    private int age; //나이
    private String profileImgPath;//프사 URL
    private String career;//경력
    private double starPoint;//별점
    private boolean isKakaoMember;//카카오톡 회원인지 여부

    private boolean isBlack;//블랙리스트인가?
    private int warningCount;//받은 경고 수


    private List<LessonDto> lessonList = new ArrayList<>();//내가 올린 강의
    private List<EvaluationDto> evaluationList = new ArrayList<>();




    @Data
    private class LessonDto {
        private Long lessonId;
        private String title;
        private LocalDateTime createdDate;//강의를 생성한 날짜
        private boolean isCompleted; //모집완료 여부, true면 모집완료, false면 모집중
        private int views;//조회수
        private LessonType lessonType; //과외 타입, PERSONAL, GROUP
        //TODO: 이건 프론트에서 알아서 처리, PERSONAL일 경우 maxStudentCount와 nowStudentCount 안보이게 알아서 설정
        private int maxStudentCount; //모집할 학생 수, PERSONAL이라면 1이 갈거임
        private int nowStudentCount; //현재 학생 수, PERSONAL이라면 모집이 완료되면 1


        public LessonDto(Lesson lesson) {
            this.lessonId = lesson.getId();
            this.title = lesson.getTitle();
            this.createdDate = lesson.getCreatedDate();
            this.isCompleted = lesson.isCompleted();
            this.views = lesson.getViews();
            if(lesson instanceof GroupLesson groupLesson){
                this.lessonType = LessonType.GROUP;
                this.maxStudentCount = groupLesson.getMaxStudentCount();
                this.nowStudentCount = groupLesson.getNowStudentCount();
            }
            else {
                this.lessonType = LessonType.PERSONAL;
                this.maxStudentCount = 1;
                if(isCompleted){
                    this.nowStudentCount = 1;
                }else {
                    this.nowStudentCount = 0;
                }
            }
        }


    }

    @Data
    private static class EvaluationDto {
        private Long evaluationId;
        private String content;
        private double startPoint;
        private String studentName;//학생의 id는 신분 보호를 위해 안알려줌

        public EvaluationDto(Evaluation evaluation) {
            evaluationId = evaluation.getId();
            content = evaluation.getContent();
            startPoint = evaluation.getStarPoint();


            if(evaluation.getStudent() ==null){
                studentName= EvaluationConstName.DEFAULT_NAME;//귀욤둥이 동훈이
            }else {
                studentName = evaluation.getStudent().getName();
            }
        }
    }


    public MyInfoTeacherDto(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.age = teacher.getAge();
        this.profileImgPath = teacher.getProfileImgPath();
        this.career = teacher.getCareer();
        this.starPoint = teacher.getStarPoint();
        this.isKakaoMember = teacher.isKakaoMember();
        this.isBlack = teacher.isBlack();
        this.warningCount = teacher.getWarningCount();


        this.lessonList = teacher.getLessonList().stream().map(lesson -> new LessonDto(lesson)).toList();//배치사이즈로 해결
        this.evaluationList =teacher.getEvaluationList().stream().map(evaluation -> new EvaluationDto(evaluation)).toList();//배치사이즈로 해결
    }
}
