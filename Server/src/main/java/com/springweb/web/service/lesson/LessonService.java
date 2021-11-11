package com.springweb.web.service.lesson;

import com.springweb.web.controller.dto.lesson.CreateLessonDto;
import com.springweb.web.controller.dto.lesson.LessonDetailDto;
import com.springweb.web.controller.dto.lesson.SearchLessonDto;
import com.springweb.web.controller.dto.lesson.UpdateLessonDto;
import com.springweb.web.domain.lesson.GroupLesson;
import com.springweb.web.domain.lesson.PersonalLesson;
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
     * 과외 신청 => 선생님께 알람 (여기서 채팅 기능을 도입해서 서로 대회하게 만든 후, 과외신청이 완료되면 과외인원 추가)을 보냄, 이미 모집완료된 강의면 오류 발생
     *
     * 과외 신청 수락 => 과외 신청시 교수님께 알람 -> 교수님이 OK 버튼을 누르면, 과외 신청이 수락됨
     *              => 이때 개인과외면 모집완료 => 그룹과외면 현재 학생수 1 추가
     *              => + TakingLesson 에 추가해야 함.
     *               =>거부하면 아쉽..^^
     *
     *
     * 과외 신청 취소 => (전제조건 : 선생님과 이미 돈과 수업 관련된 상담이 끝나서 서로 취소하기로 협의한 상태)
     *              => 모집완료된 과외일 경우 불가능, 개인과외일 경우 불가능(개인과외면 이미 신청이 수락된 순간 모집완료이므로)
     *              => 그룹과외일 경우 (똑같이 취소알람이 교수님께 감, OK 누르면 듣는 학생수 -1, TakingLesson 삭제
     *
     *
     * 과외 추천 => 동일 추천 방지
     *
     */


    //== 게시물 등록 ==//
    void create(CreateLessonDto createLessonDto) throws MemberException, LessonException, IOException;


    //== 게시물 검색 & 조회==//
    /**
     * 총 페이지 수, 현재 페이지 번호, 현재 페이지에 존재하는 과외들의 개수, 총 과외 수
     * + 강의의 간단한 내용(작성자, 제목, 모집인원 등)
     */
    SearchLessonDto search(LessonSearchCond cond, Pageable pageable);


    //== 게시물 세부정보 보기, 조회수 +1 ==//
    LessonDetailDto getLessonInfo(Long lessonId) throws LessonException;

    //== 과외 정보 수정 ==//
    void update(Long lessonId, UpdateLessonDto updateLessonDto) throws LessonException, MemberException, IOException;

    //== 과외 삭제 ==//
    void delete(Long lessonId) throws LessonException, MemberException;


    //== 과외 신청 ==//
    void apply(Long lessonId);


    //== 과외 신청 수락==//
    void accept(Long lessonId);

    //== 과외 신청 취소==//
    void cancle(Long lessonId);

    //== 과외 추천 ==//
    void clickRecommend(Long lessonId);





}
