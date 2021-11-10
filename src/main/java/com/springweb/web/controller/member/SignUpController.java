package com.springweb.web.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.kakaomemberinfo.KakaoMemberInfo;
import com.springweb.web.controller.dto.signup.BasicSignUpStudentDto;
import com.springweb.web.controller.dto.signup.BasicSignUpTeacherDto;
import com.springweb.web.controller.dto.signup.KakaoSignUpStudentDto;
import com.springweb.web.controller.dto.signup.KakaoSignUpTeacherDto;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.oauth2.OAuth2Exception;
import com.springweb.web.service.KakaoService;
import com.springweb.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final MemberService memberService;
    private final KakaoService kakaoService;


    //== 일반 회원가입 [학생]==//
    @Trace
    @PostMapping("/join/student")
    public ResponseEntity join(@ModelAttribute BasicSignUpStudentDto basicSignUpStudentDto) throws MemberException {
        memberService.save(basicSignUpStudentDto.toEntity());//Member 객체를 만들어서 회원가입
        return new ResponseEntity("환영합니다 "+ basicSignUpStudentDto.getName()+"님", HttpStatus.CREATED);
    }

    //== 일반 회원가입 [선생]==//
    @Trace
    @PostMapping("/join/teacher")
    public ResponseEntity join(@ModelAttribute BasicSignUpTeacherDto basicSignUpTeacherDto) throws MemberException {
        memberService.save(basicSignUpTeacherDto.toEntity());//Member 객체를 만들어서 회원가입
        return new ResponseEntity("환영합니다 "+ basicSignUpTeacherDto.getName()+"님", HttpStatus.CREATED);
    }



    //== 카카오 회원가입 [학생]==//
    @PostMapping("/join/student/kakao")
    public ResponseEntity joinUsingKakao(@ModelAttribute KakaoSignUpStudentDto kakaoSignUpStudentDto) throws JsonProcessingException, MemberException {
        KakaoMemberInfo kakaoMemberInfo =
                kakaoService.getKakaoInfoUsingAccessToken(kakaoSignUpStudentDto.getAccessToken());//accessToken을 사용해서 카카오에서 kakaoId를 받아옴

        kakaoSignUpStudentDto.confirmKakaoId(kakaoMemberInfo.getId());//받아온 kakaoId를 설정해준 후

        memberService.save(kakaoSignUpStudentDto.toEntity());//Member 객체를 만들어서 회원가입, 비밀번호는 UUID를 이용하여 생성

        return new ResponseEntity("환영합니다 "+ kakaoSignUpStudentDto.getName()+"님", HttpStatus.CREATED);
    }

    //== 카카오 회원가입 [선생]==//
    @PostMapping("/join/teacher/kakao")
    public ResponseEntity joinUsingKakao(@ModelAttribute KakaoSignUpTeacherDto kakaoSignUpTeacherDto) throws JsonProcessingException, MemberException {
        KakaoMemberInfo kakaoMemberInfo =
                kakaoService.getKakaoInfoUsingAccessToken(kakaoSignUpTeacherDto.getAccessToken());//accessToken을 사용해서 카카오에서 kakaoId를 받아옴

        kakaoSignUpTeacherDto.confirmKakaoId(kakaoMemberInfo.getId());//받아온 kakaoId를 설정해준 후

        memberService.save(kakaoSignUpTeacherDto.toEntity());//Member 객체를 만들어서 회원가입, 비밀번호는 UUID를 이용하여 생성

        return new ResponseEntity("환영합니다 "+ kakaoSignUpTeacherDto.getName()+"님", HttpStatus.CREATED);
    }
}
