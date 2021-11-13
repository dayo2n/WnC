package com.springweb.web.jwt;

import com.springweb.web.aop.annotation.Trace;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    public static final String AUTHORIZATION_HEADER = "Authorization";


    /**
     * JWT 토큰의 인증정보를 Security Context에 저장
     */
    @Trace
    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;//Http로 형변환

        String jwt = resolveToken(httpServletRequest);//헤더에 포함된 토큰 정보를 가져오기

        String requestURI = httpServletRequest.getRequestURI();//요청한 주소를 저장 -> 예외 처리시 사용

        log.info("들어온 토큰 정보{}",httpServletRequest.getHeader(AUTHORIZATION_HEADER));
        log.info("들어온 요청 정보{}",httpServletRequest.getRequestURI());


        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt,httpServletRequest)) {//토큰이 비어있지 않고 && 토큰이 유효하다면

            Authentication authentication = tokenProvider.getAuthentication(jwt);//토큰 정보를 이용하여 권한 생성

            SecurityContextHolder.getContext().setAuthentication(authentication);//생성한 권한을 SecurityContext에 저장

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    @Trace
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}