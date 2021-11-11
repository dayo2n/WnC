package com.springweb.web.service.evaluation;

import com.springweb.web.controller.dto.evaluation.EvaluationDto;
import com.springweb.web.controller.dto.evaluation.EvaluationTeacherDto;
import com.springweb.web.controller.dto.evaluation.SearchEvaluationTeacherDto;
import com.springweb.web.exception.BaseException;

import java.util.List;

public interface EvaluationService {
    /**
     *
     * 평가하기 => 조건 => 가입요청이 수락된 강의에 대해서만 가능(TakingLecture에만 존재하면 가능)[강의평가가 아니라 선생님 평가임]!!!!!, 1명당 1번만 평가 가능!
     * +학생만 평가 가능
     * 평가는 수정, 삭제 안돼!!!
     */

    //== 선생님 평가==//
    void evaluate(Long teacherId, EvaluationDto evaluationDto) throws BaseException;

    //== 평가 가능한 선생님 목록 보여주기 , 이건 페이징 못하겠다 ㅈㅅ ==//
    SearchEvaluationTeacherDto getEvaluatedTeacherList() throws BaseException;
}
