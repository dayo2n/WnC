package com.springweb.web.controller.evaluation;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.dto.evaluation.EvaluationDto;
import com.springweb.web.dto.evaluation.SearchEvaluationTeacherDto;
import com.springweb.web.exception.BaseException;
import com.springweb.web.service.evaluation.EvaluationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
//@Slf4j
public class EvaluationController {

    private final EvaluationService evaluationService;

    /**
     * 평가 가능한 선생님 보여주기
     */
    //@Trace
    @GetMapping("/myInfo/evaluation/teachers")
    public ResponseEntity getTeacherList() throws BaseException {
        SearchEvaluationTeacherDto result = evaluationService.getEvaluatedTeacherList();
        return new ResponseEntity(result, HttpStatus.OK);
    }


    //@Trace
    @PostMapping("/myInfo/evaluation/teachers/{teacherId}")
    public ResponseEntity evaluate(@PathVariable("teacherId") Long teacherId, @RequestBody EvaluationDto evaluationDto) throws BaseException {
        evaluationService.evaluate(teacherId,evaluationDto);
        return new ResponseEntity( HttpStatus.OK);
    }



}
