package com.springweb.web.dto.signup;

import com.springweb.web.domain.member.Member;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BasicSignUpDto {
    String getUsername();
    List<MultipartFile> getProfileImg();
    Member toEntity();

}
