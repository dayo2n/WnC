package com.springweb.web.service.member;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.controller.dto.kakaomemberinfo.KakaoMemberInfo;
import com.springweb.web.exception.oauth2.OAuth2Exception;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    /*private final String GRANT_TYPE = "authorization_code";
    private final String KAKAO_OAUTH2_CLIENT_ID = "5f53cd479060b1eafe7255fddc34297a";
    private final String REDIRECT_URL = "http://localhost:8080/login";
    private final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";*/



    private final String MEMBER_INFO_URL = "https://kapi.kakao.com/v2/user/me";

    private final String MEMBER_LEAVE_URL = "https://kapi.kakao.com/v1/user/unlink";




    /**
     * accessToken 을 이용한 유저정보 받기
     * 아이디를 반환
     */
    @Trace
    public KakaoMemberInfo getKakaoInfoUsingAccessToken(String accessToken) throws JsonProcessingException {
        log.info("토큰을 가지고 회원 정보를 가져옵니다. 토큰 정보[{}] ",accessToken);
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            log.info("카카오에 요청을 보냅니다");
            ResponseEntity<String> response = restTemplate.postForEntity(MEMBER_INFO_URL, request, String.class);///v2/user/me
            log.info("카카오에서 요청을 성공적으로 받아왔습니다[{}]",response.getBody());

            return objectMapper.readValue(response.getBody(), KakaoMemberInfo.class);//받오온 요청을 읽어서

        } catch (Exception e) {
            log.error("파싱중에 에러가 발생했습니다. {}",e.getCause());
            throw e;
        }
    }

    /**
     * 카카오 회원 탈퇴
     *
     * 카카오의 return id	Long	연결 끊기에 성공한 사용자의 회원번호
     */
    @Trace
    public KakaoMemberInfo leave(String accessToken) throws JsonProcessingException {
        log.info("토큰을 가지고 회원 탈퇴를 시작합니다. 토큰 정보[{}] ",accessToken);
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer " + accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            log.info("카카오에 탈퇴 요청을 보냅니다");
            ResponseEntity<String> response = restTemplate.postForEntity(MEMBER_LEAVE_URL, request, String.class);///v2/user/me
            log.info("카카오에서 탈퇴 요청을 성공적으로 받아왔습니다[{}]",response.getBody());

            return objectMapper.readValue(response.getBody(), KakaoMemberInfo.class);//받오온 요청을 읽어서

        } catch (Exception e) {
            log.error("파싱중에 에러가 발생했습니다. {}",e.getCause());
            throw e;
        }
    }


}
