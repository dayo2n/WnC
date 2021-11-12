package com.springweb.web.controller.dto.member;

import com.springweb.web.domain.member.Role;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class UpdateStudentDto {

    private String name;//이름
    private int age; //나이
    private MultipartFile profileImg;//프사
    private String password;
    private boolean doProfileImgChange;

}
