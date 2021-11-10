package com.springweb.web.repository.member;

import com.springweb.web.aop.annotation.Trace;
import com.springweb.web.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    @Trace
    Optional<Member> findByUsername(String username);

    @Trace
    Optional<Member> findByKakaoId(Long kakaoId);

}
