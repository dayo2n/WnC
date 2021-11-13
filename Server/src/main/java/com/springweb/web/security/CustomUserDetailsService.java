package com.springweb.web.security;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.member.Member;
import com.springweb.web.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/**
 * Authentication(인증)을 관리할 때 DB등과 같이 인메모리가 아닌
 * 저장소에 들어있는 유저정보를 가져와 사용할때 사용한다.
 *
 * DaoAuthenticationProvider 에서 사용한다.
 *
 * 조건 : username을 통해 사용자를 구해와서 UserDetails 타입으로 return 해주어야 함.
 *
 * 지금은 User를 제공해주어서 간편하게 설정할 수 있지만 과거에는 Member와 Userdetails를 이어주는 어댑터 역할을 하는
 * UserDetails를 구현한 클래스를 만들어 주기도 하였음
 *
 * 이것만 하면 인증이 되느냐? X ->
 */

@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Trace
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Member member = memberRepository.findByUsername(username).orElse(null);
        if(member ==null ){
            throw new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다.");
        }
        if (!member.isActivated()) {
            throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
        }


        return User.builder()//스프링 시큐리티의 user
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

}
