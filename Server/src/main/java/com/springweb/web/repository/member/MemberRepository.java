package com.springweb.web.repository.member;

import com.springweb.web.domain.member.Member;
import com.springweb.web.domain.member.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> , MemberRepositoryCustom{

    Optional<Member> findByUsername(String username);


    Optional<Member> findByKakaoId(Long kakaoId);


    @Query("select t from Teacher t where t.isBlack=true" )
    Optional<Teacher> findAllBlackList();

}
