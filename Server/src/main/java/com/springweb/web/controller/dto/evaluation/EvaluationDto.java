package com.springweb.web.controller.dto.evaluation;

import com.springweb.web.domain.evaluation.Evaluation;
import lombok.Data;

/**
 * 평가 작성 시 받아옴
 */
@Data
public class EvaluationDto {

    private String content; //평가내용

    private double starPoint; //별점 (1~5)

    public Evaluation toEntity(){
        return new Evaluation(content,starPoint);
    }
}
