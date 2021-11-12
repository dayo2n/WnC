package com.springweb.web.domain.member;


import com.springweb.web.domain.evaluation.Evaluation;
import com.springweb.web.domain.lesson.AppliedLesson;
import com.springweb.web.domain.lesson.TakingLesson;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@AttributeOverride(name = "id", column = @Column(name = "STUDENT_ID"))
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorValue("S")
public class Student extends Member {

    /**
     * 등록된 강의 목록
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TakingLesson> takingLessonList = new ArrayList<>();

    /**
     * 신청요청을 보낸 강의 목록
     */
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AppliedLesson> appliedLessonList = new ArrayList<>();


    @OneToMany(mappedBy = "student") //학생이 탈퇴해도 결과는 남는다. HOW ? -> 그냥 연관관계를 끊어주면 된다.
    private List<Evaluation> evaluationList = new ArrayList<>();//내가 남긴 평가들




    @Builder
    public Student(Long id, String username, String password, String name, int age, String profileImgPath, Role role, Long kakaoId, boolean isKakaoMember, boolean activated) {
        super(id, username, password, name, age, profileImgPath, role, kakaoId, isKakaoMember, activated);
    }

    public void addEvaluation(Evaluation evaluation){
        evaluationList.add(evaluation);
    }

    public void addTakingLesson(TakingLesson takingLesson){
        takingLessonList.add(takingLesson);
    }

    public void addAppliedLesson(AppliedLesson appliedLesson){
        appliedLessonList.add(appliedLesson);
    }
}
