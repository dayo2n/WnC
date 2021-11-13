package com.springweb.web.dto.admin;

import com.springweb.web.domain.member.Admin;
import com.springweb.web.domain.member.Role;
import lombok.Data;

@Data
public class SignUpAdminDto {

    private String name;
    private String username;
    private String password;


    public Admin toEntity(){
        return Admin.builder()
                .name(name)
                .username(username)
                .password(password)
                .role(Role.ADMIN)
                .activated(true)
                .build();
    }
}
