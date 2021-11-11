package com.springweb.web.controller.dto.member;

import com.springweb.web.domain.member.Teacher;
import lombok.Data;

@Data
public class SimpleTeacherDto {

    private String name;
    private String username;

    public SimpleTeacherDto(Teacher teacher) {
        this.name = teacher.getName();
        this.username = teacher.getUsername();
    }
}
