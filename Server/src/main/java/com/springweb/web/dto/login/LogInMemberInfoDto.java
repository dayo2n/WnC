package com.springweb.web.dto.login;

import com.springweb.web.domain.member.Teacher;
import lombok.Data;

@Data
public class LogInMemberInfoDto {

    private MemberType memberType;

    private String token;

    private Long id;

    private int myNoReadChatCount;
    private int myNoReadAlarm;

    private boolean isBlack;


    private boolean isKakao;
    private boolean isLogin = true;

    public LogInMemberInfoDto(Long id,String token) {
        this.id = id;
        this.token = token;
    }



    public void setStudent(){
        this.memberType = MemberType.STUDENT;
    }

    public void setTeacher(Teacher teacher){
        this.memberType = MemberType.TEACHER;
        this.isBlack=teacher.isBlack();
    }

    public void setKakao() {
        isKakao = true;
    }
    public void setBasic(){
        isKakao = false;
    }
}
