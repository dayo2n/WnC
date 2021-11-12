package com.springweb.web.service.report;

import com.springweb.web.domain.member.Teacher;
import lombok.Data;

@Data
public class BlackTeacher {

    private Long id;
    private String username;
    private String name;

    public BlackTeacher(Teacher teacher) {
        this.id = teacher.getId();
        this.username = teacher.getUsername();
        this.name = teacher.getName();
    }
}
