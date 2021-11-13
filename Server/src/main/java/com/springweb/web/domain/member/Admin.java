package com.springweb.web.domain.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@DiscriminatorValue("A")
public class Admin extends Member{

    protected Admin() {
        designateAdmin();//어드민으로 지정
    }

    @Builder
    public Admin(Long id, String username, String password, String name, int age, String profileImgPath, Role role, Long kakaoId, boolean isKakaoMember, boolean activated) {
        super(id, username, password, name, age, profileImgPath, role, kakaoId, isKakaoMember, activated);
    }




}
