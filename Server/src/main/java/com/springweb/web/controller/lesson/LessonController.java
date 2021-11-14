package com.springweb.web.controller.lesson;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.dto.lesson.CreateLessonDto;
import com.springweb.web.dto.lesson.LessonDetailDto;
import com.springweb.web.dto.lesson.SearchLessonDto;
import com.springweb.web.dto.lesson.UpdateLessonDto;
import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.valid.ValidException;
import com.springweb.web.exception.valid.ValidExceptionType;
import com.springweb.web.service.lesson.AppliedLessonService;
import com.springweb.web.service.lesson.LessonService;
import com.springweb.web.service.lesson.search.LessonSearchCond;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 강의 등록,
 * 강의 수정
 * 강의 조회
 * 강의 삭제
 * 강의 검색
 */

@RestController
@RequiredArgsConstructor
//@Slf4j
public class LessonController {

    private final LessonService lessonService;
    private final AppliedLessonService appliedLessonService;

    //== 과외 게시물 생성 ==//
    //@Trace
    @PostMapping("/lesson")
    public ResponseEntity create(@ModelAttribute CreateLessonDto createLessonDto) throws BaseException, IOException {
        lessonService.create(createLessonDto);
        return ResponseEntity.ok("포스트 등록에 성공했습니다");
    }


    //== 과외 게시물 조회 ==//
    //@Trace
    @GetMapping("/lesson/{lessonId}")
    public ResponseEntity readOne(@PathVariable("lessonId") Long lessonId) throws BaseException {
        //log.info("lessonId [{}]",lessonId);
        LessonDetailDto lessonInfo = lessonService.getLessonInfo(lessonId);//안되면 기본생성자 추가하기
        return new ResponseEntity(lessonInfo, HttpStatus.OK);
    }


    //== 과외 게시물 검색 ==//

    /**
     *     * 모든 유저에게 허용!!!!!!
     */
    //@Trace
    @GetMapping("/lesson")
    public ResponseEntity searchLesson(LessonSearchCond cond,
                                       @PageableDefault(page = 0, size = 12)//기본페이지0, 기본사이즈 12
                                               Pageable pageable) throws BaseException {


        SearchLessonDto result = lessonService.search(cond, pageable);//안되면 기본생성자 추가하기

        return new ResponseEntity(result, HttpStatus.OK);
    }



    //== 과외 게시물 수정 ==//
    //@Trace
    @PutMapping("/lesson/{lessonId}")
    public ResponseEntity update(@PathVariable("lessonId") Long lessonId,
                                  UpdateLessonDto updateLessonDto) throws BaseException, IOException {

        lessonService.update(lessonId,updateLessonDto);//안되면 기본생성자 추가하기
        return new ResponseEntity("과외 정보를 수정했습니다.", HttpStatus.CREATED);
    }


    //== 과외 게시물 삭제 ==//
    //@Trace
    @DeleteMapping("/lesson/{lessonId}")
    public ResponseEntity delete(@PathVariable("lessonId") Long lessonId,
                                  @Valid PasswordDto passwordDto, BindingResult bindingResult) throws BaseException, IOException {
        if(bindingResult.hasErrors()){
            throw new ValidException(ValidExceptionType.FORMAT_ERROR);
        }

        lessonService.delete(lessonId,passwordDto.getPassword());//안되면 기본생성자 추가하기
        return new ResponseEntity("과외를 삭제했습니다..", HttpStatus.OK);
    }



    //== 과외 신청하기 ==//
    //@Trace
    @PostMapping("/lesson/{lessonId}/apply")
    public ResponseEntity apply(@PathVariable("lessonId") Long lessonId) throws BaseException {
        appliedLessonService.apply(lessonId);

        return new ResponseEntity("가입신청을 보냈습니다",HttpStatus.OK);
    }

    //== 과외 가입 취소하기 ==//
    //@Trace
    @PostMapping("/lesson/{lessonId}/cancel")
    public ResponseEntity cancel(@PathVariable("lessonId") Long lessonId) throws BaseException {
        appliedLessonService.cancel(lessonId);
        return new ResponseEntity("가입취소를 완료했습니다",HttpStatus.OK);
    }


    //== 과외 가입 수락하기 ==//
    //@Trace
    @PostMapping("/lesson/{lessonId}/accept/{alarmId}")
    public ResponseEntity accept(@PathVariable("lessonId") Long lessonId,
                                 @PathVariable("alarmId") Long alarmId) throws BaseException {
        appliedLessonService.accept(lessonId, alarmId);
        return new ResponseEntity("가입을 승인하셨습니다",HttpStatus.OK);
    }


    //== 과외 가입 거절하기 ==//
    //@Trace
    @PostMapping("/lesson/{lessonId}/cancel/{alarmId}")
    public ResponseEntity refuse(@PathVariable("lessonId") Long lessonId,
                                 @PathVariable("alarmId") Long alarmId) throws BaseException {
        appliedLessonService.refuse(lessonId, alarmId);
        return new ResponseEntity("가입신청을 거절하셨습니다.",HttpStatus.OK);
    }


    //== 과외 모집 완료하기 ==//
    //@Trace
    @PostMapping("/lesson/{lessonId}/complete")
    public ResponseEntity completed(@PathVariable("lessonId") Long lessonId) throws BaseException {
        appliedLessonService.applyCompleted(lessonId);
        return new ResponseEntity("과외 모집을 완료하였습니다.",HttpStatus.OK);
    }


    @Data
    private static class PasswordDto{
        @NotNull
        private String password;
    }






}
