package com.springweb.web.controller.member;


import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.member.DeleteMember;
import com.springweb.web.controller.dto.member.UpdateStudentDto;
import com.springweb.web.controller.dto.member.UpdateTeacherDto;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MemberController {

    /**
     * 회원정보 수정, 회원탈퇴
     */
    private final MemberService memberService;


    @Trace
    @PutMapping("/members/student")
    public ResponseEntity update(@ModelAttribute UpdateStudentDto updateStudentDto) throws IOException, MemberException {
        memberService.update(updateStudentDto);

        return new ResponseEntity("회원 정보를 수정하였습니다.", HttpStatus.CREATED);
    }

    @Trace
    @PutMapping("/members/teacher")
    public ResponseEntity update(@ModelAttribute UpdateTeacherDto updateTeacherDto) throws IOException, MemberException {
        memberService.update(updateTeacherDto);

        return new ResponseEntity("회원 정보를 수정하였습니다.", HttpStatus.CREATED);
    }


    /**
     * 카카오톡으로 가입한 회원이라면, 알아서 탈퇴하도록 만들어야 함
     * @return
     */
    @DeleteMapping("/members")
    public ResponseEntity delete(@ModelAttribute DeleteMember deleteMember) throws MemberException {
        memberService.delete(deleteMember.getPassword());
        return new ResponseEntity("회원 탈퇴에 성공하였습니다.", HttpStatus.CREATED);
    }
}
