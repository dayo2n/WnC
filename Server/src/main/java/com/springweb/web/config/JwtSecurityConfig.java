package com.springweb.web.config;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.jwt.JwtFilter;
import com.springweb.web.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 이걸 구현해야 SecurityBuilder에 접근할 수 있는 것 같음.
 */
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;


    @Trace
    @Override
    public void configure(HttpSecurity http) {
        JwtFilter customFilter = new JwtFilter(tokenProvider);//토큰을 검사하고 유효한 토큰이 있으면 이를 저장하는 필터터
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}