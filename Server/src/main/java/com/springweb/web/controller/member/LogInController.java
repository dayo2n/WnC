package com.springweb.web.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.member.Student;
import com.springweb.web.domain.member.Teacher;
import com.springweb.web.dto.kakaomemberinfo.KakaoMemberInfo;
import com.springweb.web.dto.login.BasicLoginDto;
import com.springweb.web.dto.login.KakaoLoginDto;
import com.springweb.web.dto.login.LogInMemberInfoDto;
import com.springweb.web.dto.token.TokenDto;
import com.springweb.web.domain.member.Member;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.jwt.JwtFilter;
import com.springweb.web.jwt.TokenProvider;
import com.springweb.web.repository.member.MemberRepository;
import com.springweb.web.service.alarm.AlarmService;
import com.springweb.web.service.chat.MessageService;
import com.springweb.web.service.member.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LogInController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final MessageService messageService;
    private final KakaoService kakaoService;
    private final MemberRepository memberRepository;
    private final AlarmService alarmService;


    /**
     * 일반 로그인 username과 password로 로그인
     *
     * 로그인 결과 : 나의 id, 내가 학생인지 선생인지 여부, 내 로그인 토큰, 새로운 알림이 있는지, 새로운 메세지가 있는지
     */
    @Trace
    @PostMapping("/login")//로그인 주소
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody BasicLoginDto loginDto) throws MemberException {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        String jwt = authenticationAndGeneratingToken(authenticationToken);
        HttpHeaders httpHeaders = setTokenInHeader(jwt);

        Member member = memberRepository.findByUsername(loginDto.getUsername()).orElse(null);
        LogInMemberInfoDto logInMemberInfoDto = new LogInMemberInfoDto(member.getId(),jwt);

        if(member instanceof Student student){
            logInMemberInfoDto.setStudent();
            logInMemberInfoDto.setMyNoReadChatCount(messageService.getMyNoReadChatCount(student));
            logInMemberInfoDto.setMyNoReadAlarm(alarmService.getMyNoReadAlarm());
        }else {
            logInMemberInfoDto.setTeacher((Teacher) member); //=> 블랙리스트인지 확인해야 하므로
            logInMemberInfoDto.setMyNoReadChatCount(messageService.getMyNoReadChatCount((Teacher) member));
            logInMemberInfoDto.setMyNoReadAlarm(alarmService.getMyNoReadAlarm());
            //블랙리스트인가?

        }


        return new ResponseEntity(logInMemberInfoDto, httpHeaders, HttpStatus.OK);
    }



    /**
     * 카카오 로그인 토큰을 이용하여 로그인
     */
    @Trace
    @PostMapping("/login/kakao")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody KakaoLoginDto loginDto) throws  MemberException, JsonProcessingException {


        Member findMember = getMemberFromKakao(loginDto);//access token을 가지고 카카오에서 kakaoID를 받아온 후, 이를 로그인
        //이후부터는 위와 같은 로직

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(findMember.getUsername(), findMember.getPassword());

        String jwt = authenticationAndGeneratingToken(authenticationToken);
        HttpHeaders httpHeaders = setTokenInHeader(jwt);


        LogInMemberInfoDto logInMemberInfoDto = new LogInMemberInfoDto(findMember.getId(),jwt);

        if(findMember instanceof Student student){
            logInMemberInfoDto.setStudent();
            logInMemberInfoDto.setMyNoReadChatCount(messageService.getMyNoReadChatCount(student));
            logInMemberInfoDto.setMyNoReadAlarm(alarmService.getMyNoReadAlarm());
        }else {
            logInMemberInfoDto.setTeacher((Teacher) findMember);//이거 되나???????????????????????????????
            logInMemberInfoDto.setMyNoReadChatCount(messageService.getMyNoReadChatCount((Teacher) findMember));
            logInMemberInfoDto.setMyNoReadAlarm(alarmService.getMyNoReadAlarm());
        }


        return new ResponseEntity(logInMemberInfoDto, httpHeaders, HttpStatus.OK);
    }




    private HttpHeaders setTokenInHeader(String jwt) {
        //== 토큰 전송 로직 ==//
        //JWT를 헤더와 body에 모두 넣어준다
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);//Authorization Bearer [토큰정보]
        log.info("전송한 토큰 정보{}", jwt);
        return httpHeaders;
    }



    private String authenticationAndGeneratingToken(UsernamePasswordAuthenticationToken authenticationToken) {
        //== 권한 부여 로직 => 이후 loadUserByUsername 실행 ==//
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);//CustomDetailService의 loadByUsername이 실행
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //== 토큰 생성 로직 ==//
        return tokenProvider.createToken(authentication);
    }

    /**
     * access토큰을 사용하여 카카오에서 유저정보를 받아온 후, 이를 DB에서 조회
     */
    private Member getMemberFromKakao(KakaoLoginDto loginDto) throws MemberException, JsonProcessingException {
        //카카오에서 카카오ID를 가져온다
        KakaoMemberInfo kakaoMemberInfo =
                kakaoService.getKakaoInfoUsingAccessToken(loginDto.getAccessToken());//accessToken을 사용해서 카카오에서 kakaoId를 받아옴
        //카카오ID를 통해 user를 조회한다.
        Member findMember = memberRepository.findByKakaoId(kakaoMemberInfo.getId()).orElse(null);

        //가입이 안되어있는경우
        if (findMember == null ){
            throw new MemberException(MemberExceptionType.MUST_REGISTER);
        }
        return findMember;

    }





}
