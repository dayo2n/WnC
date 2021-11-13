package com.springweb.web.dto.signup;

import com.springweb.web.domain.member.Role;
import com.springweb.web.domain.member.Student;
import com.springweb.web.myconst.EvaluationConstName;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Data
public class KakaoSignUpStudentDto {

    private String accessToken;//인가코드는 프론트에서 알아서 하고, accessToken만 받아와서 그걸로 인증
    private String username; //카카오로 로그인 하는 경우에도 사용한다!!!!!
    private String name;//직접 입력
    private int age; //나이

    private List<MultipartFile> profileImg;//프사 URL


    private Long kakaoId;//카카오에서 가져옴, 안보내줘도 됨

    //== 카카오 로그인일 경우 ==//
    private final boolean isKakaoMember=true;


    //== 일반 회원가입 기본 설정 ==//
    private final Role role = Role.BASIC;  //BASIC, IMPORTANT, ADMIN
    private final boolean activated=true;//활성화 여부




    //== 카카오 가입시 꼭 해주어야 함 ==//
    public void confirmKakaoId(Long kakaoId){
        this.kakaoId = kakaoId;
    }


    public Student toEntity(){
        return Student.builder()
                .username(username)
                .password(UUID.randomUUID().toString().split("-")[0])//UUID를 사용
                //.password(EvaluationConstName.DEFAULT_NAME)
                .name(name)
                .age(age)
                .kakaoId(kakaoId)
                .isKakaoMember(isKakaoMember)//true
                .role(role)
                .activated(activated)
                .build();
    }
}
