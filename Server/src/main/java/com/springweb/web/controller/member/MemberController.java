package com.springweb.web.controller.member;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.kakaomemberinfo.KakaoMemberInfo;
import com.springweb.web.controller.dto.member.*;
import com.springweb.web.exception.BaseException;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.service.member.KakaoService;
import com.springweb.web.service.member.MemberService;
import com.springweb.web.controller.dto.member.SearchOneTeacherDto;
import com.springweb.web.service.member.search.TeacherSearchCond;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    /**
     * 회원정보 수정, 회원탈퇴, 내정보 보기
     */
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final KakaoService kakaoService;



    @Trace
    @GetMapping("/myInfo")
    public ResponseEntity getMyInfo() throws MemberException {
        Object myInfo = memberService.getMyInfo();
        if(myInfo instanceof MyInfoStudentDto myInfoStudentDto){
            return new ResponseEntity(myInfoStudentDto, HttpStatus.OK);
        }
        if(myInfo instanceof MyInfoTeacherDto myInfoTeacherDto){
            return new ResponseEntity(myInfoTeacherDto, HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }



    @Trace
    @PutMapping("/members/student")
    public ResponseEntity update(@ModelAttribute UpdateStudentDto updateStudentDto) throws IOException, BaseException {
        memberService.update(updateStudentDto);

        return new ResponseEntity("회원 정보를 수정하였습니다.", HttpStatus.CREATED);
    }

    @Trace
    @PutMapping("/members/teacher")
    public ResponseEntity update(@ModelAttribute UpdateTeacherDto updateTeacherDto) throws IOException, BaseException {
        memberService.update(updateTeacherDto);

        return new ResponseEntity("회원 정보를 수정하였습니다.", HttpStatus.CREATED);
    }



    @DeleteMapping("/members")
    public ResponseEntity delete(@ModelAttribute DeleteMember deleteMember) throws MemberException {
        memberService.delete(deleteMember.getPassword());
        return new ResponseEntity("회원 탈퇴에 성공하였습니다.", HttpStatus.CREATED);
    }

    /**
     * 카카오톡 회원 탈퇴
     */
    @DeleteMapping("/members/kakao")
    public ResponseEntity deleteKakao(@ModelAttribute KakaoDeleteMember kakaoDeleteMember) throws MemberException, JsonProcessingException {

        KakaoMemberInfo leaveMemberKakaoIdDto = kakaoService.leave(kakaoDeleteMember.getAccessToken());//카카오톡에서 연결을 끊은 후,


        memberService.kakaoDelete(leaveMemberKakaoIdDto.getId());
        return new ResponseEntity("카카오 회원 탈퇴에 성공하였습니다.", HttpStatus.CREATED);
    }


    /**
     * 선생님 목록 조회
     */
    @GetMapping("/members/teachers")
    public ResponseEntity searchTeacher(TeacherSearchCond cond,
                                        @PageableDefault(page = 0, size = 12)//기본페이지0, 기본사이즈 12
                                        //TODO : 별점이 없는 사람이면 어카지?????
                                        @SortDefault.SortDefaults({
                                                @SortDefault(sort = "starPoint", direction = Sort.Direction.DESC),
                                                @SortDefault(sort = "createdDate", direction = Sort.Direction.DESC)
                                                                         }) Pageable pageable) throws BaseException {

        SearchTeacherDto searchTeacherDto = memberService.searchTeacher(cond, pageable);
        return new ResponseEntity(searchTeacherDto, HttpStatus.OK);
    }

    /**
     * 선생님 목록 조회
     */
    @GetMapping("/members/teachers/{teacherId}")
    public ResponseEntity searchTeacher(@PathVariable("teacherId")Long teacherId) throws BaseException {

        SearchOneTeacherDto teacher = memberService.findTeacher(teacherId);
        return new ResponseEntity(teacher, HttpStatus.OK);
    }

}

