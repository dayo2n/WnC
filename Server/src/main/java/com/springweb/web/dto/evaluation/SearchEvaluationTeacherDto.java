package com.springweb.web.dto.evaluation;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 선생님 평가시 사용
 *
 * 페이징이 좀 어려워서 이건 포기
 */
@Data
public class SearchEvaluationTeacherDto {

    private int totalElementCount;


    private List<EvaluationTeacherDto> evaluationTeacherDtoList = new ArrayList<>();


    public SearchEvaluationTeacherDto(List<EvaluationTeacherDto> evaluationTeacherDtoList) {
        this.totalElementCount = evaluationTeacherDtoList.size();

        this.evaluationTeacherDtoList = evaluationTeacherDtoList;
    }
}



