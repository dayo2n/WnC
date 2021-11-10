package com.springweb.web.jwt.exceptionhandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springweb.web.aop.annotation.Trace;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;


    //필요한 권한이 없이 접근하려 할때 403
    @Override
    @Trace
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        String requestURI = request.getRequestURI();
        setResponse(response,requestURI);
    }

    /**
     * 한글 출력을 위해 getWriter() 사용
     */
    private void setResponse(HttpServletResponse response,String requestURI) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        String string = objectMapper.writeValueAsString(new UnauthorizedPageDto(requestURI));

        response.getWriter().write(string);

    }


    @Data
    private static class UnauthorizedPageDto {
        private final String DEFAULT_FORBIDDEN_MESSAGE = "접근 권한이 없습니다";

        private String requestURI;
        private String message = DEFAULT_FORBIDDEN_MESSAGE;


        public UnauthorizedPageDto(String requestURI) {
            this.requestURI = requestURI;
        }
    }
}