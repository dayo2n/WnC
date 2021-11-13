package com.springweb.web.dto.member;

import com.springweb.web.domain.evaluation.Evaluation;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.myconst.EvaluationConstName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TeacherDetailWithEvaluationDto {
    /**
     * 총 과외 수
     *
     * 평가 당하는 선생님 id,
     * 선생님 이름
     * 선생님 프사
     * 선생님 별점 평균
     * 선생님 경력
     * 선생님 나이
     *
     * <p>
     * ==평가 정보==
     * 평가 id
     * 평가 내용
     * 평가한 학생 (탈퇴한 학생의 경우, 알 수 없음)
     * 별점
     *
     */

    private long totalElementCount;


    private Long id;//선생님의 id
    private String name;//
    private int age; //나이
    private String profileImgPath;//프사 URL
    private String career;//경력
    private double starPointAvg;//별점 평균


    private List<EvaluationDto> evaluationDtos = new ArrayList<>();


    @Data
    private class EvaluationDto {
        private Long evaluationId;//평가id
        private String content;//평가내용
        private double starPoint;//별점
        private String studentName; //이름이 없으면 알 수 없음 or 뭐 귀여운 플로토 같은거!

        public EvaluationDto(Evaluation evaluation) {
            this.evaluationId = evaluation.getId();
            this.content = evaluation.getContent();
            this.starPoint = evaluation.getStarPoint();
            if(evaluation.getStudent() == null){
                this.studentName = EvaluationConstName.DEFAULT_NAME;
            }else{
                this.studentName = evaluation.getStudent().getName();
            }
        }
    }







    public TeacherDetailWithEvaluationDto(List<Evaluation> evaluationList, Teacher teacher) {


        this.totalElementCount = evaluationList.size();


        this.id = teacher.getId();
        this.name = teacher.getName();
        this.age = teacher.getAge();
        this.profileImgPath = teacher.getProfileImgPath();
        this.career = teacher.getCareer();
        this.starPointAvg = teacher.getStarPoint();

        this.evaluationDtos = evaluationList.stream().map(evaluation -> new EvaluationDto(evaluation)).toList();
    }
}
