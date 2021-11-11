package com.springweb.web.service.evaluation;


import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.evaluation.EvaluationDto;
import com.springweb.web.controller.dto.evaluation.EvaluationTeacherDto;
import com.springweb.web.controller.dto.evaluation.SearchEvaluationTeacherDto;
import com.springweb.web.domain.evaluation.Evaluation;
import com.springweb.web.domain.lesson.TakingLesson;
import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.evaluation.EvaluationException;
import com.springweb.web.exception.evaluation.EvaluationExceptionType;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.repository.evaluation.EvaluationRepository;
import com.springweb.web.repository.lesson.TakingLessonRepository;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class EvaluationServiceImpl implements EvaluationService{

    private final MemberRepository memberRepository;
    private final TakingLessonRepository takingLessonRepository;
    private final EvaluationRepository evaluationRepository;

    @Trace
    private String getMyUsername() throws MemberException {
        String username = SecurityUtil.getCurrentUsername().orElse(null);
        if(username == null){
            log.error("SecurityContextHolder에 있는 username을 가져오던 중 오류 발생");
            throw new MemberException(MemberExceptionType.PLEASE_LOGIN_AGAIN);
        }
        return username;
    }




    //== 선생님 평가 ==//
    @Override
    public void evaluate(Long teacherId, EvaluationDto evaluationDto) throws BaseException {
        Member member = memberRepository.findByUsername(getMyUsername()).orElse(null);
        if(member instanceof Teacher) throw new EvaluationException(EvaluationExceptionType.NO_AUTHORITY_EVALUATE);//선생님인 경우 평가할 수 없음
        Student me = (Student) member;


        //내 id와 선생님의 id를 사용해서 선생님의 강의 중 내가 들은 강의가 있나 확인,
        List<TakingLesson> takingLessonList = takingLessonRepository.findAllByTeacherIdAndStudentId(teacherId, me.getId());//teacherId만 쓰므로 패치조인 필요없음


        //선생님의 수업을 들은 적이 없는 경우, 이미 프론트에서 막을거지만 ,그래도 한번 더 검증
        if(takingLessonList.isEmpty()) throw new EvaluationException(EvaluationExceptionType.NO_AUTHORITY_EVALUATE);


        for (Evaluation myEvaluation : me.getEvaluationList()) {
            if(myEvaluation.getTeacher().getId().equals(teacherId)) throw new EvaluationException(EvaluationExceptionType.ALREADY_EVALUATE);
        }//이미 평가한 경우


        Teacher teacher = (Teacher)memberRepository.findById(teacherId).orElse(null);

        //== 평가 작성 ==//
        Evaluation evaluation = evaluationDto.toEntity();
        evaluation.confirmTeacher(teacher);//선생님께 작성된 평가에 추가
        evaluation.confirmStudent(me);//내가 작성한 평가에 추가

        evaluationRepository.save(evaluation);

    }

    //== 평가 가능한 선생님 목록 보여주기 ==//
    @Override
    public SearchEvaluationTeacherDto getEvaluatedTeacherList() throws BaseException {
        Member member = memberRepository.findByUsername(getMyUsername()).orElse(null);
        //선생님인 경우 평가 불가능
        if(member instanceof Teacher ) throw new EvaluationException(EvaluationExceptionType.NO_AUTHORITY_EVALUATE);

        Student me = (Student) member;
        List<TakingLesson> takingLessonList = takingLessonRepository.findAllWithTeacherByStudentUsername(me.getUsername());//내가 듣는 수업을 가져와서

        List<EvaluationTeacherDto> evaluationTeacherDtoList = new ArrayList<>();//내가 듣는 수업 중 평가를 안 한 성생님의 정보만 가져오기

         takingLessonList.forEach(takingLesson -> {
            for (Evaluation evaluation : me.getEvaluationList()) {
                if(evaluation.getTeacher().getId().equals(takingLesson.getLesson().getTeacher().getId())){
                    //내가 남긴 평가의 선생님과, 내가 들은 과괴의 선생님 아이디가 같은 경우, 즉 이미 평가를 한 경우
                    continue;
                }
                evaluationTeacherDtoList.add(new EvaluationTeacherDto(takingLesson.getLesson().getTeacher()));
            }
        });
         return new SearchEvaluationTeacherDto(evaluationTeacherDtoList);
    }
}
