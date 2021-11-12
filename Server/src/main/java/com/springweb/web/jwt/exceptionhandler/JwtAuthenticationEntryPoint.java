package com.springweb.web.jwt.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springweb.web.aop.annotation.Trace;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //webConfig 에서 등록
    private final ObjectMapper objectMapper;

    private final String TOKEN_EXCEPTION = "tokenException";//TokenProvider 에 있는것과 동일하게

    // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
    @Override
    @Trace
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {


        String errorMessage = (String)request.getAttribute(TOKEN_EXCEPTION);

        String requestURI = request.getRequestURI();
        setResponse(response,requestURI, errorMessage);
    }


    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response,String requestURI ,String  errorMessage) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        String string = objectMapper.writeValueAsString(new UnauthorizedPageDto(requestURI,errorMessage));//예외 생성

        response.getWriter().write(string);
    }


    @Data
    private static class UnauthorizedPageDto {
        private final String DEFAULT_REJECT_ACCESS_MESSAGE = "접근 권한이 없습니다";

        private String requestURI;
        private String message = DEFAULT_REJECT_ACCESS_MESSAGE;

        public UnauthorizedPageDto(String requestURI,String message) {
            this.requestURI = requestURI;

            if(requestURI.equals("/login")||requestURI.equals("/login/kakao")){
                message = "아이디 또는 비밀번호가 잘못되었습니다.";
            }

            if(StringUtils.hasLength(message)){
                this.message =message;
            }
        }
    }
}