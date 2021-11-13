package com.springweb.web.dto.member;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateStudentDto {

    private String name;//이름
    private int age; //나이
    private MultipartFile profileImg;//프사
    private String oldPassword;
    private String newPassword;
    private boolean doProfileImgChange;

}
