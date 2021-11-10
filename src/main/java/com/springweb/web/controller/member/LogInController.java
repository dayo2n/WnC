package com.springweb.web.controller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.kakaomemberinfo.KakaoMemberInfo;
import com.springweb.web.controller.dto.login.BasicLoginDto;
import com.springweb.web.controller.dto.login.KakaoLoginDto;
import com.springweb.web.controller.dto.token.TokenDto;
import com.springweb.web.domain.member.Member;
import com.springweb.web.exception.member.MemberException;
import com.springweb.web.exception.member.MemberExceptionType;
import com.springweb.web.exception.oauth2.OAuth2Exception;
import com.springweb.web.jwt.JwtFilter;
import com.springweb.web.jwt.TokenProvider;
import com.springweb.web.repository.MemberRepository;
import com.springweb.web.service.KakaoService;
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

    private final KakaoService kakaoService;
    private final MemberRepository memberRepository;


    /**
     * 일반 로그인 username과 password로 로그인
     */
    @Trace
    @PostMapping("/login")//로그인 주소
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody BasicLoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        String jwt = authenticationAndGeneratingToken(authenticationToken);
        HttpHeaders httpHeaders = setTokenInHeader(jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
    }



    /**
     * 카카오 로그인 토큰을 이용하여 로그인
     *
     * TODO: 문제점 :  memberRepository.findByKakaoId(kakaoMemberInfo.getId()).orElse(null);에서 한번, loadUsername에서 한번 총 두번의 쿼리가 실행
     */
    @Trace
    @PostMapping("/login/kakao")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody KakaoLoginDto loginDto) throws  MemberException, JsonProcessingException {

        log.info("getMemberFromKakao호출되나?");
        Member findMember = getMemberFromKakao(loginDto);//access token을 가지고 카카오에서 kakaoID를 받아온 후, 이를 로그인
        //이후부터는 위와 같은 로직

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(findMember.getUsername(), findMember.getPassword());

        String jwt = authenticationAndGeneratingToken(authenticationToken);
        HttpHeaders httpHeaders = setTokenInHeader(jwt);

        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
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
