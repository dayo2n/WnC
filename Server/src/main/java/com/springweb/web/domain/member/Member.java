package com.springweb.web.domain.member;

import com.springweb.web.domain.alarm.Alarm;
import com.springweb.web.domain.base.BaseTimeEntity;
import com.springweb.web.domain.evaluation.Evaluation;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(unique = true)
    private String username;//카카오로 로그인 하는 경우에도 아이디는 입력

    private String password;//카카오로 로그인 안했을때 사용, 카카오 로그인 시에는 random UUID의 첫마디 사용
    private String name;//직접 입력
    private int age; //나이

    private String profileImgPath;//프사 URL, 풀 경로 저장

    @Enumerated(EnumType.STRING)
    private Role role = Role.BASIC;  //BASIC, ADMIN,관리자가 아니면 다 BASIC


    @Column(unique = true)
    private Long kakaoId;

    private boolean isKakaoMember;


    @Column(name = "activated")
    private boolean activated;//활성화 여부


    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alarm> alarmList = new ArrayList<>();


    //== 빌더 생성자 ==//
    public Member(Long id, String username, String password, String name, int age, String profileImgPath, Role role, Long kakaoId, boolean isKakaoMember, boolean activated) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.profileImgPath = profileImgPath;
        this.role = role;
        this.kakaoId = kakaoId;
        this.isKakaoMember = isKakaoMember;
        this.activated = activated;
    }

    //== 회원가입 시 비밀번호 암호화 ==//
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
    }



    //== 회원정보 수정 ==//
    public void changeName(String name){
        this.name = name;
    }
    public void changeAge(int age){
        this.age = age;
    }
    public void changeProfileImgPath(String profileImgPath){
        this.profileImgPath = profileImgPath;
    }
    public void changePassword(String password, PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

    protected void designateAdmin(){
        role = Role.ADMIN;
    }


}
