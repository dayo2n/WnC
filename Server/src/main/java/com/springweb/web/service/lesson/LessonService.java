package com.springweb.web.service.lesson;

import com.springweb.web.controller.dto.lesson.CreateLessonDto;
import com.springweb.web.controller.dto.lesson.LessonDetailDto;
import com.springweb.web.controller.dto.lesson.SearchLessonDto;
import com.springweb.web.controller.dto.lesson.UpdateLessonDto;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.PersonalLesson;
import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.alarm.AlarmException;
import com.springweb.web.exception.lesson.LessonException;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.service.lesson.search.LessonSearchCond;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface LessonService {
    /**
     * 게시물 등록(선생님만 가능, 개인과외, 그룹과외로 설정해서 등록 가능)
     *
     * 검색 시 모집 완료된 과외는 보여주지 않음!
     *
     * 게시물 검색(검색조건 : 제목, 선생님 이름, 모집 인원, 내용)
     *
     * 게시물 전체 조회(조회조건: 조회수가 많은 순, 최근 등록일 순, 오래된 등록일 순, 개인과외만 보기, 그룹과외만 보기)
     *
     * 과외를 클릭하여 세부사항 보기(1개 조회) => 조회수 +1을 한다.
     *
     *
     *
     * 과외 정보 수정 => 이미 모집완료된 과외인 경우 불가능함,
     *              => 공통적으로는 제목, 내용, 파일 수정 가능, 그룹과외일 경우 최대인원, 모집기간 변경 가능,
     *
     *
     * 과외 삭제 =>
     *         => 모집완료된 강의의 경우 삭제 불가능
     *         => 신청한 학생이 있는 경우(신청요청이 아닌 신청요청을 수락하여, 실제로 신청된 학생) 불가능
     *         => 즉 신청한 학생이 아무도 없는 경우에만 삭제 가능
     *
     *
     *
     *
     * 과외 추천 => 동일 추천 방지
     */


    //== 게시물 등록 ==//
    void create(CreateLessonDto createLessonDto) throws IOException, BaseException;


    //== 게시물 검색 & 조회==//
    /**
     * 총 페이지 수, 현재 페이지 번호, 현재 페이지에 존재하는 과외들의 개수, 총 과외 수
     * + 강의의 간단한 내용(작성자, 제목, 모집인원 등)
     */
    SearchLessonDto search(LessonSearchCond cond, Pageable pageable);


    //== 게시물 세부정보 보기, 조회수 +1 ==//
    LessonDetailDto getLessonInfo(Long lessonId) throws BaseException;

    //== 과외 정보 수정 ==//
    void update(Long lessonId, UpdateLessonDto updateLessonDto) throws BaseException, IOException;

    //== 과외 삭제 ==//
    void delete(Long lessonId, String password) throws BaseException;







}
